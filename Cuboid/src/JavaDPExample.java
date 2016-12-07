import java.util.Map;
import java.util.HashMap;

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Animal} interface
 */
interface Animal {
    /**
     * An animal speaks
     */
    public void speak ();
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Lion} class
 */
class Lion implements Animal {
    /**
     * The lion speaks
     */
    public void speak() {
        System.out.println("ROAR");
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Mouse} class
 */
class Mouse implements Animal {
    /**
     * The mouse speaks
     */
    public void speak() {
        System.out.println("SQUEAK");
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Bison} class
 */
class Bison implements Animal {
    /**
     * The bison speaks
     */
    public void speak() {
        System.out.println("BELLOW");
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Dog} class
 */
class Dog implements Animal {
    /**
     * The dog speaks
     */
    public void speak() {
        System.out.println("woof");
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code AnimalType} class
 */
class AnimalType {
    /**
     * Create and return an animal
     * @param criteria      {@code String} how is the animal like
     * @return              {@code Animal} the animal
     */
	private static Map<String, Class<? extends Animal>> animals = new HashMap<String, Class<? extends Animal>>();
	
	
	public static void mapAnimal(String criteria, Class<? extends Animal> animalClass) {
		animals.put(criteria, animalClass);
	}
	
    public static Animal getAnimal(String criteria) {
    	try {
    		return animals.get(criteria).newInstance();
    	} catch (Exception e) {
    		return null;
    	}
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code JavaDPExample} class
 */
public class JavaDPExample {
    /**
     * Main entry
     * @param args          {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
    	AnimalType.mapAnimal("small", Mouse.class);
    	AnimalType.mapAnimal("big", Bison.class);
    	AnimalType.mapAnimal("lazy", Lion.class);
    	AnimalType.mapAnimal("loyal", Dog.class);
    	

        AnimalType.getAnimal("small").speak();    	
        AnimalType.getAnimal("big").speak();
        AnimalType.getAnimal("lazy").speak();        
        AnimalType.getAnimal("loyal").speak();
        
        try {
            AnimalType.getAnimal("smalll").speak();
        } catch (Exception e) {
            System.out.println("Unknown animal...");
        }    	
    }
}
