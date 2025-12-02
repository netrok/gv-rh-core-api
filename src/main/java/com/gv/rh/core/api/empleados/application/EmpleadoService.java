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
        if (empleadoRepository.existsByNumEmpleado(request.numEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con número " + request.numEmpleado());
        }

        Boolean activo = request.activo() != null ? request.activo() : Boolean.TRUE;

        Empleado empleado = Empleado.builder()
                .numEmpleado(request.numEmpleado())
                .nombres(request.nombres())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .telefono(request.telefono())
                .email(request.email())
                .fechaIngreso(request.fechaIngreso())
                .activo(activo)

                .fechaNacimiento(request.fechaNacimiento())
                .genero(request.genero())
                .estadoCivil(request.estadoCivil())
                .curp(request.curp())
                .rfc(request.rfc())
                .nss(request.nss())
                .foto(request.foto())

                .departamentoId(request.departamentoId())
                .puestoId(request.puestoId())
                .turnoId(request.turnoId())
                .horarioId(request.horarioId())
                .supervisorId(request.supervisorId())

                .calle(request.calle())
                .numExt(request.numExt())
                .numInt(request.numInt())
                .colonia(request.colonia())
                .municipio(request.municipio())
                .estado(request.estado())
                .cp(request.cp())
                .nacionalidad(request.nacionalidad())
                .lugarNacimiento(request.lugarNacimiento())

                .escolaridad(request.escolaridad())
                .tipoSangre(request.tipoSangre())

                .contactoNombre(request.contactoNombre())
                .contactoTelefono(request.contactoTelefono())
                .contactoParentesco(request.contactoParentesco())

                .banco(request.banco())
                .cuentaBancaria(request.cuentaBancaria())
                .clabe(request.clabe())
                .salarioBase(request.salarioBase())
                .tipoContrato(request.tipoContrato())
                .tipoJornada(request.tipoJornada())
                .fechaBaja(request.fechaBaja())
                .motivoBaja(request.motivoBaja())

                .imssRegPatronal(request.imssRegPatronal())
                .infonavitNumero(request.infonavitNumero())
                .infonavitDescuentoTipo(request.infonavitDescuentoTipo())
                .infonavitDescuentoValor(request.infonavitDescuentoValor())
                .fonacotNumero(request.fonacotNumero())

                .licenciaNumero(request.licenciaNumero())
                .licenciaTipo(request.licenciaTipo())
                .licenciaVigencia(request.licenciaVigencia())
                .build();

        Empleado guardado = empleadoRepository.save(empleado);
        return toResponse(guardado);
    }

    public EmpleadoResponse actualizar(Long id, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id " + id));

        empleado.setNumEmpleado(request.numEmpleado());
        empleado.setNombres(request.nombres());
        empleado.setApellidoPaterno(request.apellidoPaterno());
        empleado.setApellidoMaterno(request.apellidoMaterno());
        empleado.setTelefono(request.telefono());
        empleado.setEmail(request.email());
        empleado.setFechaIngreso(request.fechaIngreso());
        empleado.setActivo(request.activo());

        empleado.setFechaNacimiento(request.fechaNacimiento());
        empleado.setGenero(request.genero());
        empleado.setEstadoCivil(request.estadoCivil());
        empleado.setCurp(request.curp());
        empleado.setRfc(request.rfc());
        empleado.setNss(request.nss());
        empleado.setFoto(request.foto());

        empleado.setDepartamentoId(request.departamentoId());
        empleado.setPuestoId(request.puestoId());
        empleado.setTurnoId(request.turnoId());
        empleado.setHorarioId(request.horarioId());
        empleado.setSupervisorId(request.supervisorId());

        empleado.setCalle(request.calle());
        empleado.setNumExt(request.numExt());
        empleado.setNumInt(request.numInt());
        empleado.setColonia(request.colonia());
        empleado.setMunicipio(request.municipio());
        empleado.setEstado(request.estado());
        empleado.setCp(request.cp());
        empleado.setNacionalidad(request.nacionalidad());
        empleado.setLugarNacimiento(request.lugarNacimiento());

        empleado.setEscolaridad(request.escolaridad());
        empleado.setTipoSangre(request.tipoSangre());

        empleado.setContactoNombre(request.contactoNombre());
        empleado.setContactoTelefono(request.contactoTelefono());
        empleado.setContactoParentesco(request.contactoParentesco());

        empleado.setBanco(request.banco());
        empleado.setCuentaBancaria(request.cuentaBancaria());
        empleado.setClabe(request.clabe());
        empleado.setSalarioBase(request.salarioBase());
        empleado.setTipoContrato(request.tipoContrato());
        empleado.setTipoJornada(request.tipoJornada());
        empleado.setFechaBaja(request.fechaBaja());
        empleado.setMotivoBaja(request.motivoBaja());

        empleado.setImssRegPatronal(request.imssRegPatronal());
        empleado.setInfonavitNumero(request.infonavitNumero());
        empleado.setInfonavitDescuentoTipo(request.infonavitDescuentoTipo());
        empleado.setInfonavitDescuentoValor(request.infonavitDescuentoValor());
        empleado.setFonacotNumero(request.fonacotNumero());

        empleado.setLicenciaNumero(request.licenciaNumero());
        empleado.setLicenciaTipo(request.licenciaTipo());
        empleado.setLicenciaVigencia(request.licenciaVigencia());

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
                e.getSalarioBase(),
                e.getTipoContrato(),
                e.getTipoJornada(),
                e.getFechaBaja(),
                e.getMotivoBaja(),

                e.getImssRegPatronal(),
                e.getInfonavitNumero(),
                e.getInfonavitDescuentoTipo(),
                e.getInfonavitDescuentoValor(),
                e.getFonacotNumero(),

                e.getLicenciaNumero(),
                e.getLicenciaTipo(),
                e.getLicenciaVigencia()
        );
    }
}
