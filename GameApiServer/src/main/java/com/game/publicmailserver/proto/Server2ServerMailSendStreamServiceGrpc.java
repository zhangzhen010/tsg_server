package com.game.publicmailserver.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 邮件-向邮件服发送邮件rpc(批量流发送，没有阻塞BlockingStub方法生成) service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: publicMailMessage.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class Server2ServerMailSendStreamServiceGrpc {

  private Server2ServerMailSendStreamServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Server2ServerMailSendStreamService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail,
      com.game.bean.proto.BeanProto.RpcResult> getReqPublicMailSendMailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqPublicMailSendMail",
      requestType = com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail.class,
      responseType = com.game.bean.proto.BeanProto.RpcResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail,
      com.game.bean.proto.BeanProto.RpcResult> getReqPublicMailSendMailMethod() {
    io.grpc.MethodDescriptor<com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail, com.game.bean.proto.BeanProto.RpcResult> getReqPublicMailSendMailMethod;
    if ((getReqPublicMailSendMailMethod = Server2ServerMailSendStreamServiceGrpc.getReqPublicMailSendMailMethod) == null) {
      synchronized (Server2ServerMailSendStreamServiceGrpc.class) {
        if ((getReqPublicMailSendMailMethod = Server2ServerMailSendStreamServiceGrpc.getReqPublicMailSendMailMethod) == null) {
          Server2ServerMailSendStreamServiceGrpc.getReqPublicMailSendMailMethod = getReqPublicMailSendMailMethod =
              io.grpc.MethodDescriptor.<com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail, com.game.bean.proto.BeanProto.RpcResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqPublicMailSendMail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.bean.proto.BeanProto.RpcResult.getDefaultInstance()))
              .setSchemaDescriptor(new Server2ServerMailSendStreamServiceMethodDescriptorSupplier("reqPublicMailSendMail"))
              .build();
        }
      }
    }
    return getReqPublicMailSendMailMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Server2ServerMailSendStreamServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendStreamServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendStreamServiceStub>() {
        @java.lang.Override
        public Server2ServerMailSendStreamServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerMailSendStreamServiceStub(channel, callOptions);
        }
      };
    return Server2ServerMailSendStreamServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Server2ServerMailSendStreamServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendStreamServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendStreamServiceBlockingStub>() {
        @java.lang.Override
        public Server2ServerMailSendStreamServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerMailSendStreamServiceBlockingStub(channel, callOptions);
        }
      };
    return Server2ServerMailSendStreamServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Server2ServerMailSendStreamServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendStreamServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerMailSendStreamServiceFutureStub>() {
        @java.lang.Override
        public Server2ServerMailSendStreamServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerMailSendStreamServiceFutureStub(channel, callOptions);
        }
      };
    return Server2ServerMailSendStreamServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 邮件-向邮件服发送邮件rpc(批量流发送，没有阻塞BlockingStub方法生成) service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail> reqPublicMailSendMail(
        io.grpc.stub.StreamObserver<com.game.bean.proto.BeanProto.RpcResult> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getReqPublicMailSendMailMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Server2ServerMailSendStreamService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc(批量流发送，没有阻塞BlockingStub方法生成) service
   * </pre>
   */
  public static abstract class Server2ServerMailSendStreamServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return Server2ServerMailSendStreamServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Server2ServerMailSendStreamService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc(批量流发送，没有阻塞BlockingStub方法生成) service
   * </pre>
   */
  public static final class Server2ServerMailSendStreamServiceStub
      extends io.grpc.stub.AbstractAsyncStub<Server2ServerMailSendStreamServiceStub> {
    private Server2ServerMailSendStreamServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerMailSendStreamServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerMailSendStreamServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail> reqPublicMailSendMail(
        io.grpc.stub.StreamObserver<com.game.bean.proto.BeanProto.RpcResult> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getReqPublicMailSendMailMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Server2ServerMailSendStreamService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc(批量流发送，没有阻塞BlockingStub方法生成) service
   * </pre>
   */
  public static final class Server2ServerMailSendStreamServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<Server2ServerMailSendStreamServiceBlockingStub> {
    private Server2ServerMailSendStreamServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerMailSendStreamServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerMailSendStreamServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Server2ServerMailSendStreamService.
   * <pre>
   * 邮件-向邮件服发送邮件rpc(批量流发送，没有阻塞BlockingStub方法生成) service
   * </pre>
   */
  public static final class Server2ServerMailSendStreamServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<Server2ServerMailSendStreamServiceFutureStub> {
    private Server2ServerMailSendStreamServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerMailSendStreamServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerMailSendStreamServiceFutureStub(channel, callOptions);
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
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQ_PUBLIC_MAIL_SEND_MAIL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.reqPublicMailSendMail(
              (io.grpc.stub.StreamObserver<com.game.bean.proto.BeanProto.RpcResult>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getReqPublicMailSendMailMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.game.publicmailserver.proto.PublicMailProto.ReqPublicMailSendMail,
              com.game.bean.proto.BeanProto.RpcResult>(
                service, METHODID_REQ_PUBLIC_MAIL_SEND_MAIL)))
        .build();
  }

  private static abstract class Server2ServerMailSendStreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Server2ServerMailSendStreamServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.publicmailserver.proto.PublicMailProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server2ServerMailSendStreamService");
    }
  }

  private static final class Server2ServerMailSendStreamServiceFileDescriptorSupplier
      extends Server2ServerMailSendStreamServiceBaseDescriptorSupplier {
    Server2ServerMailSendStreamServiceFileDescriptorSupplier() {}
  }

  private static final class Server2ServerMailSendStreamServiceMethodDescriptorSupplier
      extends Server2ServerMailSendStreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    Server2ServerMailSendStreamServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (Server2ServerMailSendStreamServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Server2ServerMailSendStreamServiceFileDescriptorSupplier())
              .addMethod(getReqPublicMailSendMailMethod())
              .build();
        }
      }
    }
    return result;
  }
}
