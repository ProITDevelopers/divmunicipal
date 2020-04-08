package ao.proitconsulting.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Message {
	public  String message;
	private String status;
	private LocalDateTime time;
}
