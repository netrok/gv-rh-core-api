package com.gv.rh.core.api.empleados.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    boolean existsByNumEmpleado(String numEmpleado);

    @Query("""
        SELECT e
        FROM Empleado e
        WHERE
            ( :term IS NULL OR :term = '' OR
              lower(e.numEmpleado) LIKE lower(concat('%', :term, '%')) OR
              lower(e.nombres) LIKE lower(concat('%', :term, '%')) OR
              lower(e.apellidoPaterno) LIKE lower(concat('%', :term, '%'))
            )
        AND ( :activo IS NULL OR e.activo = :activo )
        ORDER BY e.id
        """)
    Page<Empleado> search(
            @Param("term") String term,
            @Param("activo") Boolean activo,
            Pageable pageable
    );
}
