package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 邮件-向邮件服发送邮件rpc（单个发送，需要用到阻塞BlockingStub单个调用） service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class Server2ServerMailSendServiceGrpc {

  private Server2ServerMailSendServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Server2ServerMailSendService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.bean.proto.BeanProto.MailSendData,
      com.game.bean.proto.BeanProto.RpcResult> getReqPublicMailSendMailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqPublicMailSendMail",
      requestType = com.game.bean.proto.BeanProto.MailSendData.class,
      responseType = com.game.bean.proto.BeanProto.RpcResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.bean.proto.BeanProto.MailSendData,
      com.game.bean.proto.BeanProto.RpcResult> getReqPublicMailSendMailMethod() {
    io.grpc.MethodDescriptor<com.game.bean.proto.BeanProto.MailSendData, com.game.bean.proto.BeanProto.RpcResult> getReqPublicMailSendMailMethod;
    if ((getReqPublicMailSendMailMethod = Server2ServerMailSendServiceGrpc.getReqPublicMailSendMailMethod) == null) {
      synchronized (Server2ServerMailSendServiceGrpc.class) {
        if ((getReqPublicMailSendMailMethod = Server2ServerMailSendServiceGrpc.getReqPublicMailSendMailMethod) == null) {
          Server2ServerMailSendServiceGrpc.getReqPublicMailSendMailMethod = getReqPublicMailSendMailMethod =
              io.grpc.MethodDescriptor.<com.game.bean.proto.BeanProto.MailSendData, com.game.bean.proto.BeanProto.RpcResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqPublicMailSendMail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.bean.proto.BeanProto.MailSendData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.bean.proto.BeanProto.RpcResult.getDefaultInstance()))
              .setSchemaDescriptor(new Server2ServerMailSendServiceMethodDescriptorSupplier("reqPublicMailSendMail"))
              .build();
        }
      }
    }
    return getReqPublicMailSendMailMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Server2ServerMailSendServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendServiceStub>() {
        @java.lang.Override
        public Server2ServerMailSendServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerMailSendServiceStub(channel, callOptions);
        }
      };
    return Server2ServerMailSendServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Server2ServerMailSendServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendServiceBlockingStub>() {
        @java.lang.Override
        public Server2ServerMailSendServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerMailSendServiceBlockingStub(channel, callOptions);
        }
      };
    return Server2ServerMailSendServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Server2ServerMailSendServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendServiceFutureStub>() {
        @java.lang.Override
        public Server2ServerMailSendServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerMailSendServiceFutureStub(channel, callOptions);
        }
      };
    return Server2ServerMailSendServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 邮件-向邮件服发送邮件rpc（单个发送，需要用到阻塞BlockingStub单个调用） service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqPublicMailSendMail(com.game.bean.proto.BeanProto.MailSendData request,
        io.grpc.stub.StreamObserver<com.game.bean.proto.BeanProto.RpcResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqPublicMailSendMailMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Server2ServerMailSendService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc（单个发送，需要用到阻塞BlockingStub单个调用） service
   * </pre>
   */
  public static abstract class Server2ServerMailSendServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return Server2ServerMailSendServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Server2ServerMailSendService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc（单个发送，需要用到阻塞BlockingStub单个调用） service
   * </pre>
   */
  public static final class Server2ServerMailSendServiceStub
      extends io.grpc.stub.AbstractAsyncStub<Server2ServerMailSendServiceStub> {
    private Server2ServerMailSendServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerMailSendServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerMailSendServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqPublicMailSendMail(com.game.bean.proto.BeanProto.MailSendData request,
        io.grpc.stub.StreamObserver<com.game.bean.proto.BeanProto.RpcResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqPublicMailSendMailMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Server2ServerMailSendService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc（单个发送，需要用到阻塞BlockingStub单个调用） service
   * </pre>
   */
  public static final class Server2ServerMailSendServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<Server2ServerMailSendServiceBlockingStub> {
    private Server2ServerMailSendServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerMailSendServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerMailSendServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.game.bean.proto.BeanProto.RpcResult reqPublicMailSendMail(com.game.bean.proto.BeanProto.MailSendData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqPublicMailSendMailMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Server2ServerMailSendService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc（单个发送，需要用到阻塞BlockingStub单个调用） service
   * </pre>
   */
  public static final class Server2ServerMailSendServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<Server2ServerMailSendServiceFutureStub> {
    private Server2ServerMailSendServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerMailSendServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerMailSendServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.game.bean.proto.BeanProto.RpcResult> reqPublicMailSendMail(
        com.game.bean.proto.BeanProto.MailSendData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqPublicMailSendMailMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_PUBLIC_MAIL_SEND_MAIL = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQ_PUBLIC_MAIL_SEND_MAIL:
          serviceImpl.reqPublicMailSendMail((com.game.bean.proto.BeanProto.MailSendData) request,
              (io.grpc.stub.StreamObserver<com.game.bean.proto.BeanProto.RpcResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getReqPublicMailSendMailMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.bean.proto.BeanProto.MailSendData,
              com.game.bean.proto.BeanProto.RpcResult>(
                service, METHODID_REQ_PUBLIC_MAIL_SEND_MAIL)))
        .build();
  }

  private static abstract class Server2ServerMailSendServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Server2ServerMailSendServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server2ServerMailSendService");
    }
  }

  private static final class Server2ServerMailSendServiceFileDescriptorSupplier
      extends Server2ServerMailSendServiceBaseDescriptorSupplier {
    Server2ServerMailSendServiceFileDescriptorSupplier() {}
  }

  private static final class Server2ServerMailSendServiceMethodDescriptorSupplier
      extends Server2ServerMailSendServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    Server2ServerMailSendServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (Server2ServerMailSendServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Server2ServerMailSendServiceFileDescriptorSupplier())
              .addMethod(getReqPublicMailSendMailMethod())
              .build();
        }
      }
    }
    return result;
  }
}
