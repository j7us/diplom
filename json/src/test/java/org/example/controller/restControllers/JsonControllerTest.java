package org.example.controller.restControllers;

import org.example.config.SecurityConfiguration;
import org.example.exception.ConflictException;
import org.example.exception.NoAccessException;
import org.example.repository.ManagerRepository;
import org.example.service.*;
import org.example.service.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.example.controller.restControllers.TestTemplate.buildManagerWithPermit;
import static org.example.controller.restControllers.TestTemplate.buildUserManagerWithoutPermit;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EnterpriseController.class)
@Import(SecurityConfiguration.class)
public class JsonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    VehicleService vehicleService;
    @MockitoBean
    BrandService brandService;
    @MockitoBean
    EnterpriseService enterpriseService;
    @MockitoBean
    DriverService driverService;
    @MockitoBean
    JwtService jwtService;
    @MockitoBean
    ManagerRepository managerRepository;
    @MockitoBean
    JobExecutionService jobExecutionService;

    @Test
    void anonymousUserRequestTest() throws Exception{
        mockMvc.perform(delete("/api/v1/enterprises/1"))
                .andExpect(status().is(401));
    }

    @Test
    void userAuthenticatedGetTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("username");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildUserManagerWithoutPermit()));

        mockMvc.perform(get("/api/v1/enterprises/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().is(403));
    }

    @Test
    void managerAuthenticatedNoAccessTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("manager1");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildManagerWithPermit()));
        when(enterpriseService.getById(anyString(), any())).thenThrow(NoAccessException.class);

        mockMvc.perform(get("/api/v1/enterprises/1b29fa5a-dcf5-4914-b7a7-a7bcc5750495")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().is(403));
    }

    @Test
    void managerAuthenticatedWithWrongVariableTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("manager1");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildManagerWithPermit()));

        mockMvc.perform(get("/api/v1/enterprises/wrongvariable")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().is(400));
    }

    @Test
    void deleteWithConflictTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("manager1");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildManagerWithPermit()));
        doThrow(ConflictException.class).when(enterpriseService).deleteById(anyString(), any());

        mockMvc.perform(delete("/api/v1/enterprises/1b29fa5a-dcf5-4914-b7a7-a7bcc5750495")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().is(409));
    }

    @Test
    void getSuccessTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("manager1");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildManagerWithPermit()));

        mockMvc.perform(get("/api/v1/enterprises/1b29fa5a-dcf5-4914-b7a7-a7bcc5750495")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }
    @Test
    void postSuccessTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("manager1");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildManagerWithPermit()));

        mockMvc.perform(post("/api/v1/enterprises/create")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                      "id": "1b29fa5a-dcf5-4914-b7a7-a7bcc5750495",
                                      "name": "aaa",
                                      "city": "bbb"
                                   }
                                 """))
                .andExpect(status().is(201));
    }

    @Test
    void putSuccessTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("manager1");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildManagerWithPermit()));

        mockMvc.perform(put("/api/v1/enterprises/1b29fa5a-dcf5-4914-b7a7-a7bcc5750495")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                      "id": "1b29fa5a-dcf5-4914-b7a7-a7bcc5750495",
                                      "name": "aaa",
                                      "city": "bbb"
                                   }
                                 """))
                .andExpect(status().is(200));
    }

    @Test
    void deleteSuccessTest() throws Exception{
        when(jwtService.isValidAccess(any())).thenReturn(true);
        when(jwtService.extractUsername(anyString())).thenReturn("manager1");
        when(managerRepository.findByUsername(anyString())).thenReturn(Optional.of(buildManagerWithPermit()));

        mockMvc.perform(delete("/api/v1/enterprises/1b29fa5a-dcf5-4914-b7a7-a7bcc5750495")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().is(204));
    }
}
