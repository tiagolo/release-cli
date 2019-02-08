Feature: Lonework Release workflow

  Scenario: Nova branch para release candidates
    Given Apartir da branch develop
    When Executado o workflow para criação da branch próxima versão
    Then É criada uma branch com o prefixo release/
    And A branch develop é incrementada para a próxima versão de desenvolvimento