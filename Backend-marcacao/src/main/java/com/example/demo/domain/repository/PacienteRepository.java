package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
