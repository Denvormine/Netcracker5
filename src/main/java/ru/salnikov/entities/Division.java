package ru.salnikov.entities;

import ru.vsu.lab.entities.IDivision;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name ="division")
@XmlAccessorType(XmlAccessType.FIELD)
public class Division implements IDivision {
    @XmlElement
    private Integer id;
    @XmlElement
    private String name;

    public Division(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Division() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id != null && id >= 0) {
            this.id = id;
        }
        else {
            this.id = 0;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        else {
            this.name = "defaultName";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return Objects.equals(id, division.id) &&
                Objects.equals(name, division.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ru.salnikov.entities.Division{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
