#  Exercice 5 (Spring security) :

Objectif : mise en oeuvre de spring security sur la couche web et sur la couche applicative

*****

1. Ajouter les dépendance Maven vers
	* `spring-security-config 4.2.7.RELEASE`
	* `spring-security-web 4.2.7.RELEASE`

    Observer les dépendances indirectes vers spring-*
   
2. Dans la classe d'initialisation (`WebAppInitializer`), overrider la méthode `getServletFilters` afin de renvoyer un Array de `Filter` contenant le filtre de sécurité  : 
    - classe du filtre : `DelegatingFilterProxy`
    - nom (à passer dans le constructeur du filtre) : `springSecurityFilterChain`	

   Relancer l'application web, comprendre le problème posé, et comprendre en quoi la classe de configuration dédiée à la sécurité est nécessaire.

3. Ajouter à la classe d'intialisation (WebAppInitializer) une classe statique de configuration dédiée à la sécurité. Celle-ci doit : 
    - étendre `WebSecurityConfigurerAdapter`
    - étre annotée par les bonnes annotations
    - être prise en compte au démarrage de l'application.

4. Essayer d'accéder à la route `/books`, observer la réaction du filtre.

5. Dans la classe de configuration créer en 3, redéfinir la méthode 
    ```
    void configure(AuthenticationManagerBuilder auth)
    ```
    Ajouter un utilisateur : nom = john.doe, password = azerty, permissions (authorities) = fcc
    puis essayer d'accéder à nouveau à la route `/books` et constater le succès de l'authentification.
    
    Pour se déconnecter il faudra ecrire : 
    
	```
	<form method="post" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="submit"/>
	</form>	
	``` 

6. Pour personnaliser la page de login de logout-success : modifier la classe de configuration `MvcConfiguration`, ajouter cette méthode : 
	```
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/logout-success").setViewName("logout-success");
		registry.addViewController("/login").setViewName("login");
	}
	```
    puis rédéfinir la méthode `void configure(HttpSecurity http)` dans la classe de configuration de la sécurité :
    ```
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/login").permitAll()
            .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/logout-success").permitAll();
    }
    ```


7. Ajouter au projet ex3 la dépendance vers `spring-security-core (4.2.7.RELEASE)`

8. Réfléchir à un moyen d'appliquer un contrôle de sécurité : n'importe quel utilisateur ne peut pas réaliser n'importe quel cas d'utilisation.

9. Faire en sorte que le `SecurityPreProcessorImpl` soit injecté dans le `CommandProcessorImpl`

10. Relancer le test unitaire `testFindBookCommand`, comprendre le problème posé et le résoudre.
