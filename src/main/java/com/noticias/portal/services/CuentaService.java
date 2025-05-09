package com.noticias.portal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.noticias.portal.dtos.CuentaDTO;
import com.noticias.portal.models.Cuenta;
import com.noticias.portal.repositories.CuentaRepository;
import com.noticias.portal.mappers.CuentaMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CuentaService implements UserDetailsService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cuenta cuenta = cuentaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        // Crear una lista con el rol del usuario (con el prefijo ROLE_)
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + cuenta.getRol().name()));
        
        log.info("Usuario {} autenticado con rol: {}", cuenta.getEmail(), "ROLE_" + cuenta.getRol().name());
        
        return new User(
                cuenta.getEmail(),
                cuenta.getClave(),
                authorities);
    }

    public List<CuentaDTO> listarTodasDTO() {
        return cuentaRepository.findAll()
                .stream()
                .map(cuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CuentaDTO crearCuenta(CuentaDTO dto) {
        if (cuentaRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe");
        }
        Cuenta cuenta = cuentaMapper.toEntity(dto);
        cuentaRepository.save(cuenta);
        return cuentaMapper.toDTO(cuenta);
    }

    public CuentaDTO actualizarCuenta(CuentaDTO dto) {
        Cuenta cuenta = cuentaRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuentaMapper.updateEntity(dto, cuenta);
        cuentaRepository.save(cuenta);
        return cuentaMapper.toDTO(cuenta);
    }

    public void eliminarCuenta(String id) {
        cuentaRepository.deleteById(id);
    }

}

