# Jrcos
Java Recursive Class Oriented Serializer

- Use any class to save and load
- Use public on a field to mark it as Serializable
- **final** and **transient** fields are ignored

- Classes in a list will be created, so use an empty constructor for that or write an adapter in the Mapping class. 
- Empty arrays will be created automaticly


## Settings: (in Mapping.java)

    throwObjectNullException
> Should a exception be thrown when a object couldn't be created

    boolean overwriteLists
> Should the instances be added or the list overwritten

    boolean recursiveFields 
> Should use an recursive function to get all fields in a class, including the superclasses

    boolean useDefaultCase 
> Should the default case be used if a value couldn't be found or cast

    boolean defaultBooleanCase 
> Default case or every boolean

    Number defaultNumberCase 
> Default case for every number

    char defaultCharacter 
> Default case for every character

    String defaultString 
> Default case for every string

    Class defaultClass 
> Default case for every class

    Object defaultObject 
> Default case for every object

  
# Code

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
