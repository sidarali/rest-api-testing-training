package org.mantisbt.model;

import java.util.Objects;

public class ViewState {

    private int id;
    private String name;
    private String label;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ViewState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewState viewState = (ViewState) o;
        return id == viewState.id &&
                Objects.equals(name, viewState.name) &&
                Objects.equals(label, viewState.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, label);
    }
}
