package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_role_details")
public class TbRoleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_role_details_id") // old: id
    private Long pkRoleDetailsId;

    @ManyToOne
    @JoinColumn(name = "col_role_id", nullable = false) // old: role_id
    private TbRoleMaster role;

    @ManyToOne
    @JoinColumn(name = "col_form_id", nullable = false) // old: form_id
    private TbFormMaster form;

    @Column(name = "col_seq_no") // old: seq_no
    private Integer seqNo;

    @Column(name = "col_is_allowed") // old: is_allowed
    private Byte isAllowed;

    @Column(name = "col_created_modified_date") // old: created_modified_date
    private LocalDateTime createdModifiedDate;

    @Column(name = "col_read_only") // old: read_only
    private String readOnly;

    @Column(name = "col_archive_flag") // old: archive_flag
    private String archiveFlag;

//    @ManyToOne
//    @JoinColumn(name = "col_client_id", nullable = false) // old: client_id
//    private TbClientMaster client;

    @Column(name = "col_show_in_menu") // old: show_in_menu
    private Byte showInMenu;
}
