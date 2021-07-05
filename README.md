# Jrcos
Java Recursive Class Oriented Serializer

- Use any class to save and load
- Use public on a field to mark it as Serializable
- **final** and **transient** fields are ignored

- Classes in a list will be created, so use an empty constructor for that or write an adapter in the Mapping class. 
- Empty arrays will be created automaticly


## Settings: (in Mapping.java)

> Should a exception be thrown when a object couldn't be created

    throwObjectNullException

> Should the instances be added or the list overwritten

    boolean overwriteLists

> Should use an recursive function to get all fields in a class, including the superclasses

    boolean recursiveFields 

> Should the default case be used if a value couldn't be found or cast

    boolean useDefaultCase 

> Default case or every boolean

    boolean defaultBooleanCase 

> Default case for every number

    Number defaultNumberCase 

> Default case for every character

    char defaultCharacter 

> Default case for every string

    String defaultString 

> Default case for every class

    Class defaultClass 

> Default case for every object

    Object defaultObject 


  
# Code

full Example: [SimpleTest.java](https://github.com/Plixo2/Jrcos/blob/master/src/main/java/test/SimpleTest.java)

## Save

    // convert to Json
    JsonElement toJson = Serializer.getJson(dataReference);
    
    // and save
    Util.saveJsonObj(file,toJson);

## Load

    // load from file
    JsonElement fromFile = Util.loadFromJson(file);
    
    // create empty reference without data
    Object emptyReference = new ArrayList<>();
    
    // load into the empty reference
    emptyReference = Initializer.getObject(emptyReference,fromFile);
    
    

