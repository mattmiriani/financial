package com.matt.financecontrol.config.security.service;

import com.matt.financecontrol.model.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements UserDetailsService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public AuthorityService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return subjectRepository.findSubjectByUsername(username);
    }

}
