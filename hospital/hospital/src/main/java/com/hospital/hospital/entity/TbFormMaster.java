package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_form_master")
public class TbFormMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_form_id")
    private Long pkFormId;

    @Column(name = "col_name")
    private String name;

    @Column(name = "col_parent_id")
    private Integer parentId;

    @Column(name = "col_link")
    private String link;

    @Column(name = "col_created_modified_date")
    private LocalDateTime createdModifiedDate;

    @Column(name = "col_read_only")
    private String readOnly;

    @Column(name = "col_archive_flag")
    private String archiveFlag;

    @ManyToOne
    @JoinColumn(name = "col_client_id", nullable = false)
    private TbClientMaster client;
}
