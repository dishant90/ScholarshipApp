package com.tripleS.service;

import java.util.List;
import com.tripleS.model.EntityBankDetails;
import com.tripleS.model.StudentCurriculumRecord;

public interface StudentCurriculumRecordService {
	List<StudentCurriculumRecord> findByFileNo(String fileNo);
	StudentCurriculumRecord save(StudentCurriculumRecord studentCurriculumRecord);
	void delete(int id);
	StudentCurriculumRecord findById(int id);
}
