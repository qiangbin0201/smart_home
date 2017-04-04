package com.smart.home.utils.ua;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class UserActionProxyFactory {

	private boolean isNeedUpload = true;

	public UserActionProxyFactory(boolean isNeedUpload) {
		this.isNeedUpload = isNeedUpload;
	}

	public Object newProxyInstance(UserActionIn proxy) {
		InvocationHandler handler = new UserActionHandler(proxy);
		return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), handler);
	}

	private class UserActionHandler implements InvocationHandler {
		private UserActionIn proxy = null;

		public UserActionHandler(UserActionIn proxy) {
			this.proxy = proxy;
		}

		@Override
		public Object invoke(Object object, Method method, Object[] args) throws Throwable {
			Object result = null;
			try {
				if (UserActionProxyFactory.this.isNeedUpload) {
					UserActionAnnotation annotation = method.getAnnotation(UserActionAnnotation.class);
					if (annotation != null && annotation.needUpload()) {
						result = method.invoke(this.proxy, args);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	}

}
