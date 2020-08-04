package com.assignment.application.repo;

import com.assignment.application.entity.CompleteInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompleteCompInfoRepo {

    List<CompleteInfo> companyCompleteList(Long companyId);

}
