package taskmodels;


public class HttpReturnPackage
{
    private Object objectResult;
    private int requestResponseCode;

    public HttpReturnPackage()
    {

    }

    public HttpReturnPackage(Object objectResult, int requestResponseCode) {
        this.objectResult = objectResult;
        this.requestResponseCode = requestResponseCode;
    }

    public Object getObjectResult() {
        return objectResult;
    }

    public void setObjectResult(Object objectResult) {
        this.objectResult = objectResult;
    }

    public int getRequestResponseCode() {
        return requestResponseCode;
    }

    public void setRequestResponseCode(int requestResponseCode) {
        this.requestResponseCode = requestResponseCode;
    }
}
