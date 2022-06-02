package main.businesslogic.shift;

/**
 * Cook
 */
public class Cook {
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String surname;
    private int id;

    public Cook(String name, String surname, int id) {
        this.id = id;
        this.surname = surname;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cook other = (Cook) obj;
        return this.getId() == other.getId() && this.getSurname().equals(other.getSurname())
                && this.getName().equals(other.getName());

    }

}