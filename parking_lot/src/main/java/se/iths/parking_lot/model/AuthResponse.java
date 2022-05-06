package se.iths.parking_lot.model;

public class AuthResponse {
    private final String jwtToken;

    public AuthResponse(String jwt) {
        this.jwtToken = jwt;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
