package com.gv.rh.core.api.empleados.application;

import com.gv.rh.core.api.empleados.application.dto.EmpleadoCreateRequest;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoResponse;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoUpdateRequest;
import com.gv.rh.core.api.empleados.domain.Empleado;
import com.gv.rh.core.api.empleados.domain.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public Page<EmpleadoResponse> listar(Pageable pageable, String q, Boolean activo) {
        String term = (q == null || q.isBlank()) ? null : q.trim();
        return empleadoRepository
                .search(term, activo, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse obtener(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id " + id));
        return toResponse(empleado);
    }

    public EmpleadoResponse crear(EmpleadoCreateRequest request) {
        String numEmpleado = trimToNull(request.numEmpleado());
        if (numEmpleado == null) {
            throw new IllegalArgumentException("El número de empleado es obligatorio");
        }

        if (empleadoRepository.existsByNumEmpleado(numEmpleado)) {
            throw new IllegalArgumentException("Ya existe un empleado con número " + numEmpleado);
        }

        Boolean activo = request.activo() != null ? request.activo() : Boolean.TRUE;

        Empleado empleado = Empleado.builder()
                .numEmpleado(numEmpleado)
                .nombres(trimToNull(request.nombres()))
                .apellidoPaterno(trimToNull(request.apellidoPaterno()))
                .apellidoMaterno(trimToNull(request.apellidoMaterno()))
                .telefono(trimToNull(request.telefono()))
                .email(trimToNull(request.email()))
                .fechaIngreso(request.fechaIngreso())
                .activo(activo)

                .fechaNacimiento(request.fechaNacimiento())
                .genero(trimToNull(request.genero()))
                .estadoCivil(trimToNull(request.estadoCivil()))
                .curp(trimToNull(request.curp()))
                .rfc(trimToNull(request.rfc()))
                .nss(trimToNull(request.nss()))
                .foto(trimToNull(request.foto()))

                .departamentoId(request.departamentoId())
                .puestoId(request.puestoId())
                .turnoId(request.turnoId())
                .horarioId(request.horarioId())
                .supervisorId(request.supervisorId())

                .calle(trimToNull(request.calle()))
                .numExt(trimToNull(request.numExt()))
                .numInt(trimToNull(request.numInt()))
                .colonia(trimToNull(request.colonia()))
                .municipio(trimToNull(request.municipio()))
                .estado(trimToNull(request.estado()))
                .cp(trimToNull(request.cp()))
                .nacionalidad(trimToNull(request.nacionalidad()))
                .lugarNacimiento(trimToNull(request.lugarNacimiento()))

                .escolaridad(trimToNull(request.escolaridad()))
                .tipoSangre(trimToNull(request.tipoSangre()))

                .contactoNombre(trimToNull(request.contactoNombre()))
                .contactoTelefono(trimToNull(request.contactoTelefono()))
                .contactoParentesco(trimToNull(request.contactoParentesco()))

                .banco(trimToNull(request.banco()))
                .cuentaBancaria(trimToNull(request.cuentaBancaria()))
                .clabe(trimToNull(request.clabe()))
                .tipoContrato(trimToNull(request.tipoContrato()))
                .tipoJornada(trimToNull(request.tipoJornada()))
                .fechaBaja(request.fechaBaja())
                .motivoBaja(trimToNull(request.motivoBaja()))

                .imssRegPatronal(trimToNull(request.imssRegPatronal()))
                .infonavitNumero(trimToNull(request.infonavitNumero()))
                .fonacotNumero(trimToNull(request.fonacotNumero()))
                .build();

        Empleado guardado = empleadoRepository.save(empleado);
        return toResponse(guardado);
    }

    public EmpleadoResponse actualizar(Long id, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id " + id));

        // Validar cambio de numEmpleado sin duplicar
        String nuevoNumEmpleado = trimToNull(request.numEmpleado());
        if (nuevoNumEmpleado == null) {
            throw new IllegalArgumentException("El número de empleado es obligatorio");
        }
        if (!nuevoNumEmpleado.equals(empleado.getNumEmpleado())
                && empleadoRepository.existsByNumEmpleado(nuevoNumEmpleado)) {
            throw new IllegalArgumentException("Ya existe un empleado con número " + nuevoNumEmpleado);
        }

        empleado.setNumEmpleado(nuevoNumEmpleado);
        empleado.setNombres(trimToNull(request.nombres()));
        empleado.setApellidoPaterno(trimToNull(request.apellidoPaterno()));
        empleado.setApellidoMaterno(trimToNull(request.apellidoMaterno()));
        empleado.setTelefono(trimToNull(request.telefono()));
        empleado.setEmail(trimToNull(request.email()));
        empleado.setFechaIngreso(request.fechaIngreso());
        empleado.setActivo(request.activo() != null ? request.activo() : Boolean.TRUE);

        empleado.setFechaNacimiento(request.fechaNacimiento());
        empleado.setGenero(trimToNull(request.genero()));
        empleado.setEstadoCivil(trimToNull(request.estadoCivil()));
        empleado.setCurp(trimToNull(request.curp()));
        empleado.setRfc(trimToNull(request.rfc()));
        empleado.setNss(trimToNull(request.nss()));
        empleado.setFoto(trimToNull(request.foto()));

        empleado.setDepartamentoId(request.departamentoId());
        empleado.setPuestoId(request.puestoId());
        empleado.setTurnoId(request.turnoId());
        empleado.setHorarioId(request.horarioId());
        empleado.setSupervisorId(request.supervisorId());

        empleado.setCalle(trimToNull(request.calle()));
        empleado.setNumExt(trimToNull(request.numExt()));
        empleado.setNumInt(trimToNull(request.numInt()));
        empleado.setColonia(trimToNull(request.colonia()));
        empleado.setMunicipio(trimToNull(request.municipio()));
        empleado.setEstado(trimToNull(request.estado()));
        empleado.setCp(trimToNull(request.cp()));
        empleado.setNacionalidad(trimToNull(request.nacionalidad()));
        empleado.setLugarNacimiento(trimToNull(request.lugarNacimiento()));

        empleado.setEscolaridad(trimToNull(request.escolaridad()));
        empleado.setTipoSangre(trimToNull(request.tipoSangre()));

        empleado.setContactoNombre(trimToNull(request.contactoNombre()));
        empleado.setContactoTelefono(trimToNull(request.contactoTelefono()));
        empleado.setContactoParentesco(trimToNull(request.contactoParentesco()));

        empleado.setBanco(trimToNull(request.banco()));
        empleado.setCuentaBancaria(trimToNull(request.cuentaBancaria()));
        empleado.setClabe(trimToNull(request.clabe()));
        empleado.setTipoContrato(trimToNull(request.tipoContrato()));
        empleado.setTipoJornada(trimToNull(request.tipoJornada()));
        empleado.setFechaBaja(request.fechaBaja());
        empleado.setMotivoBaja(trimToNull(request.motivoBaja()));

        empleado.setImssRegPatronal(trimToNull(request.imssRegPatronal()));
        empleado.setInfonavitNumero(trimToNull(request.infonavitNumero()));
        empleado.setFonacotNumero(trimToNull(request.fonacotNumero()));

        Empleado actualizado = empleadoRepository.save(empleado);
        return toResponse(actualizado);
    }

    public void eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Empleado no encontrado con id " + id);
        }
        empleadoRepository.deleteById(id);
    }

    private EmpleadoResponse toResponse(Empleado e) {
        return new EmpleadoResponse(
                e.getId(),
                e.getNumEmpleado(),
                e.getNombres(),
                e.getApellidoPaterno(),
                e.getApellidoMaterno(),
                e.getTelefono(),
                e.getEmail(),
                e.getFechaIngreso(),
                e.getActivo(),

                e.getFechaNacimiento(),
                e.getGenero(),
                e.getEstadoCivil(),
                e.getCurp(),
                e.getRfc(),
                e.getNss(),
                e.getFoto(),

                e.getDepartamentoId(),
                e.getPuestoId(),
                e.getTurnoId(),
                e.getHorarioId(),
                e.getSupervisorId(),

                e.getCalle(),
                e.getNumExt(),
                e.getNumInt(),
                e.getColonia(),
                e.getMunicipio(),
                e.getEstado(),
                e.getCp(),
                e.getNacionalidad(),
                e.getLugarNacimiento(),

                e.getEscolaridad(),
                e.getTipoSangre(),

                e.getContactoNombre(),
                e.getContactoTelefono(),
                e.getContactoParentesco(),

                e.getBanco(),
                e.getCuentaBancaria(),
                e.getClabe(),
                e.getTipoContrato(),
                e.getTipoJornada(),
                e.getFechaBaja(),
                e.getMotivoBaja(),

                e.getImssRegPatronal(),
                e.getInfonavitNumero(),
                e.getFonacotNumero()
        );
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
