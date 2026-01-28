package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tb_client_master")
public class TbClientMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_client_id")
    private Long pkClientId;

    @Column(name = "col_address")
    private String address;

    @Column(name = "col_email_id", unique = true)
    private String emailId;

    @Column(name = "col_mobile_no", unique = true)
    private String mobileNo;

    // password SHOULD NOT be unique → removed unique=true
    @Column(name = "col_password")
    private String password;

//    @ManyToOne
//    @JoinColumn(name = "col_role_id", referencedColumnName = "pk_role_id")
//    private TbRoleMaster role;

    @Column(name = "col_status")
    private Byte status;

    @Column(name = "col_org_name")
    private String orgName;

    @Column(name = "col_created_modified_date")
    private LocalDateTime createdModifiedDate;

    @Column(name = "col_read_only")
    private String readOnly;

    @Column(name = "col_archive_flag")
    private String archiveFlag;


    @Column(name = "col_expiry_date")
    private LocalDate expiryDate;

    @Column(name = "col_client_count")
    private Integer clientCount;

    @Column(name = "col_logo")
    private String logo;

    // One client has many users/admins
    @OneToMany(mappedBy = "client")
    private List<TbUserMaster> admins;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<TbDepartment> departments;

    // ✅ NEW COLUMN
    @Column(name = "col_patient_count", nullable = false)
    private Integer patientCount = 0;
}


