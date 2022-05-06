package com.example.myapplication.classes;

public class getId {

    public int get(String url) {
        int count = 0;
        String id = "";
        for (int i = 0; i < url.length(); i++) {
            char a = url.charAt(i);

            if (count == 5) {
                if (a != '/')
                    id += a;
            }

            if (a == '/') {
                count++;
            }

        }

        return Integer.parseInt(id);
    }

}
