package com.bttoy.service_task_template;


public class ListExampleItem {

    private String title;
    private String descr1;
    private String descr2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListExampleItem)) return false;
        ListExampleItem that = (ListExampleItem) o;
        return getTitle().equals(that.getTitle()) &&
                getDescr1().equals(that.getDescr1()) &&
                getDescr2().equals(that.getDescr2());
    }

    ListExampleItem(String title, String descr1, String descr2) {
        this.title = title;
        this.descr1 = descr1;
        this.descr2 = descr2;
    }

    String getTitle() {
        return title;
    }

    String getDescr1() {
        return descr1;
    }

    String getDescr2() {
        return descr2;
    }

}
