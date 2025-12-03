package com.gv.rh.core.api.empleados.report;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpleadoFichaReportRow {

    // Identidad / estado
    private String nombreCompleto;
    private String numEmpleado;
    private String iniciales;
    private Boolean activo;

    // Puesto / organización
    private String puesto;
    private String departamento;
    private String fechaIngreso;
    private String supervisorNombre;

    // Contacto / domicilio
    private String telefono;
    private String email;
    private String direccionCompleta;

    // Identificadores personales
    private String curp;
    private String rfc;
    private String nss;

    // Datos personales extra
    private String fechaNacimiento;
    private String genero;
    private String estadoCivil;
    private String nacionalidad;
    private String lugarNacimiento;
    private String tipoSangre;

    // Datos laborales / bancarios
    private String banco;
    private String cuentaBancaria;
    private String clabe;
    private String tipoContrato;
    private String tipoJornada;
    private String fechaBaja;
    private String motivoBaja;

    // Seguridad social / créditos
    private String imssRegPatronal;
    private String infonavitNumero;
    private String fonacotNumero;

    // Contacto emergencia
    private String contactoNombre;
    private String contactoParentesco;
    private String contactoTelefono;

    // ===== Getters y Setters (Jasper usa los getters) =====

}
