# Gson + 



    
   - all public or @Serialize annotated, non final, non static, non transient  fields are saved
   - @Hidefield to not save a field
   - @Optional if the field can be null, and should not be created


# setup
```java
      GsonPlusConfig.setOverwriteLists(true);
      GsonPlusConfig.setClassLoader(Main.class.getClassLoader()); 
```

     
# save to a file
```java
        GsonPlus gsonPlus = new GsonPlus();
        try {
	        JsonElement element = gsonPlus.toJson(player);
	        Util.saveJsonObj(location,element);
        } catch (Exception e) {
        	e.printStackTrace();
        }
```       
        
# load from a file

```java
           GsonPlusBuilder gsonPlusBuilder = new GsonPlusBuilder();
           try {
	            JsonObject object = Util.loadFromJson(location);
	            gsonPlusBuilder.create(player,object);
           } catch (Exception e) {
	            e.printStackTrace();
           }
```
