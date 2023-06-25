package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDTOStudent {


	private String userName;

	private String vorname;

	private String nachname;
	private String mail;
	private String password;
	private int registrationDate;
}
