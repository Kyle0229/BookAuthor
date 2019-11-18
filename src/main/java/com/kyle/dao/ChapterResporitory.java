package com.kyle.dao;

import com.kyle.domain.Chapter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterResporitory extends JpaRepository<Chapter,Integer> {
}
