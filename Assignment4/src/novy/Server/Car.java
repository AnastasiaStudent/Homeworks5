package novy.Server;

import java.io.Serializable;

/**
 * Car class is our produkt which produce and store
 */
public class Car implements Serializable {

    private String name;
    private int age;

    /**
     * @param age
     * @param name
     */
    public Car(int age, String name) {
        this.age = age;
        this.name = name;
    }

    /**
     * @return Car was consumed
     */
    @Override public String toString() {
        return "Car{" + "name='" + name + '\'' + ", age=" + age + "} was ";
    }

    public Car() {}

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
