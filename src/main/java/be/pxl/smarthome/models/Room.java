package be.pxl.smarthome.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name= "rooms_id")
    private List<Light> lights = new ArrayList<>();

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name= "rooms_id")
    private List<User> accesUsers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public void addLight(Light light){
        this.lights.add(light);
    }

    public List<User> getAccesUsers() {
        return accesUsers;
    }

    public void setAccesUsers(List<User> accesUsers) {
        this.accesUsers = accesUsers;
    }
}
