package com.tripleS.service;

import com.tripleS.model.StudentFile;

public interface StudentFileService {
	StudentFile findByFileNo(int fileNo);
	StudentFile update(StudentFile studentFile);
	StudentFile save(StudentFile studentFile);
	boolean existsByFileNo(int fileNo);
}
