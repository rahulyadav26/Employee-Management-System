package com.assignment.application.repo;

import com.assignment.application.entity.CompleteCompInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompleteCompInfoRepo {

    List<CompleteCompInfo> companyCompleteList(Long companyId);

}
