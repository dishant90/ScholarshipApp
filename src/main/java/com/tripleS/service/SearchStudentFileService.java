package com.tripleS.service;

import java.util.List;

import com.tripleS.model.SearchStudentFile;
import com.tripleS.model.StudentFile;

public interface SearchStudentFileService {
	List<StudentFile> search(SearchStudentFile searchStudentFile);
	
}
