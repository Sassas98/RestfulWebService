package marvin.work.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Order (2)
@Component
public class SecondFilter implements Filter {    
	
	@Override
    public void destroy() {}
	
    @Override    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {
        System.out.println("Secondo Filtro");
        filterchain.doFilter(request, response);    
    }
    
    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}
    
}
