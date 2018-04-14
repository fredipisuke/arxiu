package cat.reisigualada.reis.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String cryptPassword;
    // Perfils
    private Set<Role> roles;
    private Set<Long> selectedRoles;
    // Impresores
    private Printer printer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Transient
	public String getCryptPassword() {
		return cryptPassword;
	}

	public void setCryptPassword(String cryptPassword) {
		this.cryptPassword = cryptPassword;
	}

	@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
	public Set<Long> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(Set<Long> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "user_printer", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "printer_id") })   
	public Printer getPrinter() {
		return printer;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", passwordConfirm=");
		builder.append(passwordConfirm);
		builder.append(", cryptPassword=");
		builder.append(cryptPassword);
		builder.append(", roles=");
		builder.append(roles);
		builder.append(", selectedRoles=");
		builder.append(selectedRoles);
		builder.append(", printer=");
		builder.append(printer);
		builder.append("]");
		return builder.toString();
	}
}
