package com.med.security.service.reports;

import com.med.security.domain.Report;
import com.med.security.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * USER STORY :
 */
@Service
@RequiredArgsConstructor
public class ReportsLookUpUseCase {

    private final ReportRepository reportRepository;

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Report findById(Integer id) {
        return reportRepository.findById(id).orElseThrow();
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
