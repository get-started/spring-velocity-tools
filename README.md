# spring-velocity-tools
## Usage
```xml
<bean class="org.springframework.web.servlet.view.velocity.VelocityViewResolver">
    <property name="toolboxConfigLocation" value=".."/>
    <property name="viewClass" value="org.spring.velocity.view.VelocityToolbox2View"/>
</bean>
```