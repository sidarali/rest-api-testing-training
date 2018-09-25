package org.mantisbt.model;

import java.util.Objects;

public class Version {

    private String name;
    private boolean released;
    private boolean obsolete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    @Override
    public String toString() {
        return "Version{" +
                "name='" + name + '\'' +
                ", released=" + released +
                ", obsolete=" + obsolete +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return released == version.released &&
                obsolete == version.obsolete &&
                Objects.equals(name, version.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, released, obsolete);
    }
}
