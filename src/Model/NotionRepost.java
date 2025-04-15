package Model;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import notion.api.v1.*;
    import java.util.Collections;
    import java.util.Map;


public class NotionRepost implements IRepository {
    private String databaseId;
    private final String titleColumnName = "Identifier";
    private final NotionClient client;
    public NotionRepost(String apiToken, String databaseId) {
        // Crear cliente de Notion
        this.client = new NotionClient(apiToken);

        // Configurar cliente HTTP adecuado y tiempos de espera
        client.setHttpClient(new OkHttp5Client(60000,60000,60000));

        // Configurar loggers
        client.setLogger(new Slf4jLogger());
        
        // Silenciar/Activar los registros de log de Notion API
        // Ver en nivel debug los mensajes de depuración
        System.setProperty("notion.api.v1.logging.StdoutLogger", "debug");

        // Nivel más alto de log para NO ver mensajes de depuración
        //System.setProperty("notion.api.v1.logging.StdoutLogger", "off");

        this.databaseId = databaseId;
    }
    @Override
    public Task addTask(Task t) throws RepositoryException{
            try {
                SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
                if (t==null) {
                    throw new RepositoryException("La tarea no puede ser nula.\n");
                }
                Map<String, PageProperty> properties = Map.of(
                    "Identifier", createTitleProperty(String.valueOf(t.getIdentifier())),
                    "Titulo", createRichTextProperty(t.getTitle()!=null ? t.getTitle():""),
                    "Fecha de Inicio",  createDateProperty(d.format(t.getDate())),
                    "Contenido",  createRichTextProperty(t.getContent()!=null ? t.getContent():""),
                    "Prioridad", createNumberProperty(t.getPriority()),
                    "EstimatedDuration", createNumberProperty(t.getEstimatedDuration()),
                    "Completada", createCheckboxProperty(t.isCompleted())
                );
                if(findPageIdByIdentifier(Long.toString(t.getIdentifier()),titleColumnName)!=null){
                    throw new RepositoryException("Ya existe una tarea con el ID:"+t.getIdentifier()+"en el registro.\n");
                }
                else
                {
                    PageParent parent = PageParent.database(databaseId);
                    CreatePageRequest request = new CreatePageRequest(parent, properties);
                    Page response = client.createPage(request);
                    if (response==null ||response.getId()==null) {
                        throw new RepositoryException("La creacion de la pagina fallo o la API devolvio un valor null.\n");
                    }
                    return t;
                }
            } catch (Exception e) {
                throw new RepositoryException("Error:"+e.getMessage());
            }
    }
    @Override
    public void modifyTask(Task t)throws RepositoryException {
        if (t==null) 
                {
                    throw new RepositoryException("La tarea no puede ser nula.\n");
                }
        try {
                String pageId = findPageIdByIdentifier(String.valueOf(t.getIdentifier()), titleColumnName);
                if (pageId == null) {
                    throw new RepositoryException("No se encontró un registro con el Identifier: " + t.getIdentifier());
                }
                SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");

                // Crear las propiedades actualizadas
                Map<String, PageProperty> updatedProperties = Map.of(
                        "Titulo", createRichTextProperty(t.getTitle()!=null ? t.getTitle():""),
                        "Fecha de Inicio",  createDateProperty(d.format(t.getDate())),
                        "Contenido",  createRichTextProperty(t.getContent()!=null?t.getContent():""),
                        "Prioridad", createNumberProperty(t.getPriority()),
                        "EstimatedDuration", createNumberProperty(t.getEstimatedDuration()),
                        "Completada", createCheckboxProperty(t.isCompleted())
                );
                // Crear la solicitud de actualización
                UpdatePageRequest updateRequest = new UpdatePageRequest(pageId, updatedProperties);
                client.updatePage(updateRequest);
        } catch (Exception e) {
            throw new RepositoryException("Error en la modificacion:"+e.getMessage());
        }
    }
    private String findPageIdByIdentifier(String identifier, String titleColumnName) throws RepositoryException {
        if (identifier==null || identifier.isBlank()||titleColumnName==null||titleColumnName.isBlank()) {
            throw new RepositoryException("El identificador o el nombre de la columna estaban vacios.\n");
        }
        try {
            QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);
            QueryResults queryResults = client.queryDatabase(queryRequest);

            for (Page page : queryResults.getResults()) 
            {
                Map<String, PageProperty> properties = page.getProperties();
                if (!properties.containsKey(titleColumnName))
                {
                    throw new RepositoryException("La propiedad : "+titleColumnName+"no existe en las paginas.\n");   
                }
                PageProperty p = properties.get(titleColumnName);
                if (p.getTitle()==null || p.getTitle().isEmpty()) 
                {
                    throw new RepositoryException("El campo de titulo esta vacio en una de las columnas\n");
                }
                String content = p.getTitle().get(0).getText().getContent();
                if(identifier.equals(content))
                {
                            return page.getId();
                }
            }
            }catch (Exception e) {
                throw new RepositoryException("Error en la busqueda del ID:"+e.getMessage());
        }
        return null;
    }
// Eliminar (archivar) un registro por Identifier
    @Override
    public void removeTask(Task t) throws RepositoryException{
        if (t==null) 
                {
                    throw new RepositoryException("La tarea no puede ser nula.\n");
                }
        try {
            String pageId = findPageIdByIdentifier(String.valueOf(t.getIdentifier()),titleColumnName);
            if (pageId == null) {
                throw new RepositoryException("No se encontró un registro con el Identifier: " + t.getIdentifier());
            }
            // Archivar la página
            UpdatePageRequest updateRequest = new UpdatePageRequest(pageId, Collections.emptyMap(), true);
            client.updatePage(updateRequest);
        } catch (Exception e) {
            throw new RepositoryException("Error en la eliminacion: " + e.getMessage());
        }
    }


    @Override
    public ArrayList<Task> getAllTasks() throws RepositoryException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            // Crear la solicitud para consultar la base de datos
            QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);

            // Ejecutar la consulta
            QueryResults queryResults = client.queryDatabase(queryRequest);
            if (queryResults==null||queryResults.getResults()==null) {
                throw new RepositoryException("La consulta devolvio valores no validos");           
            }
            // Procesar los resultados
            for (Page page : queryResults.getResults()) {
                Map<String, PageProperty> properties = page.getProperties();
                Task t = mapPageToTask(page.getId(), properties);
                if (t != null) {
                    tasks.add(t);   
                }
            }
        } catch (Exception e) {
            throw new RepositoryException("Error al Listar las tareas:"+e.getMessage());
        }
        return tasks;
    }
    private PageProperty createTitleProperty(String title) {
        RichText idText = new RichText();
        idText.setText(new Text(title));
        PageProperty idProperty = new PageProperty();
        idProperty.setTitle(Collections.singletonList(idText));
        return idProperty;
    }

    // Metodos auxiliares para crear propiedades de página
    private PageProperty createRichTextProperty(String text) {
        RichText richText = new RichText();
        richText.setText(new Text(text));
        PageProperty property = new PageProperty();
        property.setRichText(Collections.singletonList(richText));
        return property;
    }

    private PageProperty createNumberProperty(Integer number) {
        PageProperty property = new PageProperty();
        property.setNumber(number);
        return property;
    }

    private PageProperty createDateProperty(String date) {
        PageProperty property = new PageProperty();
        PageProperty.Date dateProperty = new PageProperty.Date();
        dateProperty.setStart(date);
        property.setDate(dateProperty);
        return property;
    }

    private PageProperty createCheckboxProperty(boolean checked) {
        PageProperty property = new PageProperty();
        property.setCheckbox(checked);
        return property;
    }
    private Task mapPageToTask(String pageId, Map<String, PageProperty> properties) throws RepositoryException {
        try {
            SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
            if (!properties.containsKey("Identifier") || properties.get("Identifier") == null) {
                throw new RepositoryException("La propiedad 'Identifier' es nula o no existe en la página: " + pageId);
            }
            Task t = new Task();
            t.setIdentifier(Integer.parseInt(properties.get("Identifier").getTitle().get(0).getText().getContent()));
            t.setTitle(properties.get("Titulo").getRichText().get(0).getText().getContent());
            t.setDate(d.parse(properties.get("Fecha de Inicio").getDate().getStart()));
            t.setContent(properties.get("Contenido").getRichText().get(0).getText().getContent());
            t.setPriority(properties.get("Prioridad").getNumber().intValue());
            t.setEstimatedDuration(properties.get("EstimatedDuration").getNumber().intValue());
            t.setCompleted(properties.get("Completada").getCheckbox());
            return t;
        } catch (Exception e) {
            throw new RepositoryException("Error al mapear la pagina :"+pageId+"\n");
        }
    }
    @Override
    public int CoP(int indice)throws RepositoryException {
        try {
            String pageId=findPageIdByIdentifier(String.valueOf(indice),"Identifier");
            if (pageId==null) {
                return 3;
            }
            QueryDatabaseRequest request = new QueryDatabaseRequest(databaseId);
            QueryResults results = client.queryDatabase(request);
            Task t = null;
            for (Page page : results.getResults()) {
                Map<String, PageProperty> properties = page.getProperties();
                if (properties.containsKey("Identifier")) {
                    String id = properties.get("Identifier").getTitle().get(0).getText().getContent();
                    if (id.equals(String.valueOf(indice))) {
                        t=mapPageToTask(page.getId(), properties);
                        break;                      
                    }                   
                }
            }
            if (t==null) {
                return 3;
            }
            boolean newEstate= !t.isCompleted();
            Map<String,PageProperty> update = Map.of(
                "Completada",createCheckboxProperty(newEstate)
            );
            UpdatePageRequest up = new UpdatePageRequest(pageId, update,false);
            client.updatePage(up);
            return 0;
        } catch (Exception e) {
            throw new RepositoryException("Error al alternar estados:"+e.getMessage());
        }
    }
}
