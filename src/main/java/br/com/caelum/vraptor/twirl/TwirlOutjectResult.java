package br.com.caelum.vraptor.twirl;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.events.MethodExecuted;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.observer.OutjectResult;
import play.twirl.api.BufferedContent;

import javax.ejb.ApplicationException;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Specializes
@ApplicationException
public class TwirlOutjectResult extends OutjectResult {

	private final TypeNameExtractor extractor;
	private final HttpServletResponse response;

	/**
	 * @deprecated
	 */
	protected TwirlOutjectResult() {
		this(null, null);
	}

	@Inject
	public TwirlOutjectResult(TypeNameExtractor extractor, HttpServletResponse response) {
		super(extractor);
		this.extractor = extractor;
		this.response = response;
	}

	@Override
	public void outject(@Observes MethodExecuted event, Result result, MethodInfo methodInfo) {
		Object methodResult = event.getMethodInfo().getResult();
		if (methodResult instanceof BufferedContent) {
			BufferedContent twirlResult = (BufferedContent) methodResult;
			PrintWriter writer = getWriter();
			writer.print(twirlResult.body());
			writer.close();
			return;
		}
		super.outject(event, result, methodInfo);
	}

	private PrintWriter getWriter() {
		try {
			return response.getWriter();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
