package com.gv.rh.core.api.empleados.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;   // Para usar @Default

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_empleado", length = 20, nullable = false, unique = true)
    private String numEmpleado;

    @Column(name = "nombres", length = 150, nullable = false)
    private String nombres;

    @Column(name = "apellido_paterno", length = 150, nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 150)
    private String apellidoMaterno;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Default
    @Column(name = "activo", nullable = false)
    private Boolean activo = Boolean.TRUE;

    // ==== NUEVOS CAMPOS BÁSICOS ====
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "estado_civil", length = 30)
    private String estadoCivil;

    @Column(name = "curp", length = 18)
    private String curp;

    @Column(name = "rfc", length = 13)
    private String rfc;

    @Column(name = "nss", length = 15)
    private String nss;

    @Column(name = "foto", length = 255)
    private String foto;

    // ==== FK / RELACIONES (por ahora como IDs simples) ====
    @Column(name = "departamento_id")
    private Long departamentoId;

    @Column(name = "puesto_id")
    private Long puestoId;

    @Column(name = "turno_id")
    private Long turnoId;

    @Column(name = "horario_id")
    private Long horarioId;

    @Column(name = "supervisor_id")
    private Long supervisorId;

    // ==== NOMBRES DE CATÁLOGO (para ficha / reportes) ====
    @Column(name = "puesto_nombre", length = 150)
    private String puestoNombre;

    @Column(name = "departamento_nombre", length = 150)
    private String departamentoNombre;

    @Column(name = "supervisor_nombre", length = 200)
    private String supervisorNombre;

    // ==== DOMICILIO ====
    @Column(name = "calle", length = 150)
    private String calle;

    @Column(name = "num_ext", length = 20)
    private String numExt;

    @Column(name = "num_int", length = 20)
    private String numInt;

    @Column(name = "colonia", length = 150)
    private String colonia;

    @Column(name = "municipio", length = 150)
    private String municipio;

    @Column(name = "estado", length = 150)
    private String estado;

    @Column(name = "cp", length = 10)
    private String cp;

    @Column(name = "nacionalidad", length = 80)
    private String nacionalidad;

    @Column(name = "lugar_nacimiento", length = 150)
    private String lugarNacimiento;

    // ==== DATOS MÉDICOS / ESCOLARIDAD ====
    @Column(name = "escolaridad", length = 80)
    private String escolaridad;

    @Column(name = "tipo_sangre", length = 10)
    private String tipoSangre;

    // ==== CONTACTO DE EMERGENCIA ====
    @Column(name = "contacto_nombre", length = 150)
    private String contactoNombre;

    @Column(name = "contacto_telefono", length = 30)
    private String contactoTelefono;

    @Column(name = "contacto_parentesco", length = 80)
    private String contactoParentesco;

    // ==== BANCARIO / NÓMINA ====
    @Column(name = "banco", length = 80)
    private String banco;

    @Column(name = "cuenta_bancaria", length = 30)
    private String cuentaBancaria;

    @Column(name = "clabe", length = 30)
    private String clabe;

    @Column(name = "tipo_contrato", length = 50)
    private String tipoContrato;

    @Column(name = "tipo_jornada", length = 50)
    private String tipoJornada;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Column(name = "motivo_baja", length = 150)
    private String motivoBaja;

    // ==== IMSS / INFONAVIT / FONACOT ====
    @Column(name = "imss_reg_patronal", length = 50)
    private String imssRegPatronal;

    @Column(name = "infonavit_numero", length = 30)
    private String infonavitNumero;

    @Column(name = "fonacot_numero", length = 30)
    private String fonacotNumero;
}
