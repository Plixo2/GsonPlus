# Gson + 

this libary needs Gson

- setup:


      GsonPlusConfig.setOverwriteLists(true); 
    
      GsonPlusConfig.setUseDefaultCase(false);
    
      GsonPlusConfig.setClassLoader(Main.class.getClassLoader()); 

- with Annotations:

    for use with Annotations:
    
   - @Serialize to save a field

   - @Optional if the file can be null, and should not be created



          GsonPlusConfig.setAnnotationsUse(true); 
    
     
    

          
- without Annotations

   - all public, non final, non static and non transient fields are saved


          GsonPlusConfig.setAnnotationsUse(false); 
          
          
      
- save to a file: 


        GsonPlus gsonPlus = new GsonPlus();
        try {
	        JsonElement element = gsonPlus.toJson(player);
	        Util.saveJsonObj(location,element);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        
- load from a file


     - classes need an empty constructor or an adapter (create adapters in GsonPlusBuilder)



           GsonPlusBuilder gsonPlusBuilder = new GsonPlusBuilder();
           try {
	            JsonObject object = Util.loadFromJson(location);
	            gsonPlusBuilder.create(player,object);
           } catch (Exception e) {
	            e.printStackTrace();
           }

