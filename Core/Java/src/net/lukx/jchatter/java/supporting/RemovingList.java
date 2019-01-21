package net.lukx.jchatter.java.supporting;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.*;

public class RemovingList<T extends Node> implements List<T> {

    private List<T> list;
    protected Pane parent;

    public RemovingList(Pane parent){
        this.parent = parent;
        list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        parent.getChildren().add(t);
        return list.add(t);
    }

    public void removeSilent(int index){
        parent.getChildren().remove(index);
    }

    public void removeSilent(T element){
        parent.getChildren().remove(element);
    }

    @Override
    public boolean remove(Object o) {
        parent.getChildren().remove(o);
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        parent.getChildren().addAll(c);
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        parent.getChildren().addAll(c);
        return list.addAll(index,c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        parent.getChildren().removeAll(c);
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        parent.getChildren().removeAll(list);
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index,element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index,element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex,toIndex);
    }
}
