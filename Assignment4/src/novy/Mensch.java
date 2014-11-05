package novy;

/**
 * Mensch class is our produkt which produce and store
 */
public class Mensch {

    private String name;
    private int age;

    public Mensch() {

    }

    /**
     * @param age
     * @param name
     */
    public Mensch(int age, String name) {
        this.age = age;
        this.name = name;
    }

    /**
     * @return Mensch was consumed
     */
    @Override public String toString() {
        return "Mensch{" +
            "name='" + name + '\'' +
            ", age=" + age +
            "} was consumed";
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(int age) {
        this.age = age;

    }

}
