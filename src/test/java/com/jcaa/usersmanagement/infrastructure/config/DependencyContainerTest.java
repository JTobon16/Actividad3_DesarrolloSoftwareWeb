package com.jcaa.usersmanagement.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for DependencyContainer.
 *
 * <p>Covers: full wiring succeeds and exposes a non-null UserController, the same UserController
 * instance is returned on every call (immutable composition graph), and the constructor completes
 * without a real database because HikariCP initialises its pool lazily.
 */
@DisplayName("DependencyContainer")
@ExtendWith(MockitoExtension.class)
class DependencyContainerTest {

  @Mock private Connection mockConnection;

  // ── constructor + userController() — happy path

  @Test
  @DisplayName(
      "constructor wires all dependencies and userController() returns a non-null UserController")
  void shouldWireAllDependenciesAndExposeNonNullUserController() {
    // Arrange
    try (final MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager
          .when(
              () ->
                  DriverManager.getConnection(
                      any(String.class), any(String.class), any(String.class)))
          .thenReturn(mockConnection);

      // Act
      final DependencyContainer container = new DependencyContainer();

      // Assert
      assertNotNull(
          container.userController(),
          "userController() must return a non-null UserController after successful construction");
    }
  }

  // ── userController() — immutable composition graph

  @Test
  @DisplayName("userController() returns the same UserController instance on every call")
  void shouldReturnTheSameUserControllerInstanceOnEveryCall() {
    // Arrange
    try (final MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager
          .when(
              () ->
                  DriverManager.getConnection(
                      any(String.class), any(String.class), any(String.class)))
          .thenReturn(mockConnection);
      final DependencyContainer container = new DependencyContainer();

      // Act
      final UserController first = container.userController();
      final UserController second = container.userController();

      // Assert
      assertSame(first, second, "userController() must return the same instance on every call");
    }
  }

  // ── constructor — HikariCP lazy pool: no connection made during construction

  @Test
  @DisplayName("constructor succeeds without a reachable database because HikariCP pool is lazy")
  void shouldConstructSuccessfullyBecauseHikariCpIsLazy() {
    // With HikariCP DataSource the connection pool initialises lazily — no database connection
    // is attempted during DependencyContainer construction.  Connection failures surface only
    // when the first SQL query is executed by a repository method.

    // Act + Assert
    assertDoesNotThrow(
        DependencyContainer::new,
        "DependencyContainer must succeed during construction even without a reachable database");
  }
}
