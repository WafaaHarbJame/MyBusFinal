package com.mybus.mybusapp.classes;

import java.util.ArrayList;

public  class ArrayClass {
    private int id;
    private String name;

    public ArrayClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArrayClass() {
    }

    public  void Insert(ArrayList<ArrayClass> arrayList, int id, String name) {
        arrayList.add(new ArrayClass(id, name));


    }

    public  void IndexOfById(ArrayList<ArrayClass> arrayList, int Id) {
        arrayList.indexOf(arrayList.indexOf(Id));


    }

    public  void RemoveAll(ArrayList<ArrayClass> arrayList) {
        RemoveAll(arrayList);

    }

    public  void printArray(ArrayList<ArrayClass> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            ArrayClass arrayClass = arrayList.get(i);
            System.out.println("Id:".concat(" ") + arrayClass.getId() + ",".concat("Name").concat(" ") + arrayClass.getName());

        }

    }

    public static void main(String[] args) {
        ArrayClass arrayClass = new ArrayClass();
        ArrayList<ArrayClass> newArrayList = new ArrayList<>();

        //Adding
        arrayClass.Insert(newArrayList,1,"Moh");
        arrayClass.Insert(newArrayList,2,"Moh");
        arrayClass.Insert(newArrayList,2,"Moh");

        // print
        arrayClass.printArray(newArrayList);

        //index by id
        arrayClass.IndexOfById(newArrayList,1);

        //remove
        arrayClass.RemoveAll(newArrayList);



    }

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

}
