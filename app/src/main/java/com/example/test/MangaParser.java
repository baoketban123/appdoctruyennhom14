package com.example.test;

import java.util.List;

public abstract class MangaParser {
    public abstract List<Manga> searchManga(String query);
    public abstract List<Manga> nextPage();
}