package com.example.services.impl;

import com.example.domain.model.Subject;
import com.example.reposistories.impl.SubjectRepositoryLogicImpl;
import com.example.services.SubjectService;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {

        private final SubjectRepositoryLogicImpl repository;

        public SubjectServiceImpl(SubjectRepositoryLogicImpl repository) {
            this.repository = repository;
        }
        @Override
        public List<Subject> listar() {
            return repository.listar();
        }

        @Override
        public Subject porId(Long id) {
            return repository.porId(id);
        }

        @Override
        public void guardar(Subject t) {
            repository.guardar(t);
        }

        @Override
        public void eliminar(Long id) {
            repository.eliminar(id);
        }

}
