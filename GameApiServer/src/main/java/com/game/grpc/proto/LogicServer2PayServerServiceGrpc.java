package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 逻辑服向充值服rpc service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogicServer2PayServerServiceGrpc {

  private LogicServer2PayServerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "LogicServer2PayServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder,
      com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder> getReqCreateOrderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqCreateOrder",
      requestType = com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder.class,
      responseType = com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder,
      com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder> getReqCreateOrderMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder, com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder> getReqCreateOrderMethod;
    if ((getReqCreateOrderMethod = LogicServer2PayServerServiceGrpc.getReqCreateOrderMethod) == null) {
      synchronized (LogicServer2PayServerServiceGrpc.class) {
        if ((getReqCreateOrderMethod = LogicServer2PayServerServiceGrpc.getReqCreateOrderMethod) == null) {
          LogicServer2PayServerServiceGrpc.getReqCreateOrderMethod = getReqCreateOrderMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder, com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqCreateOrder"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder.getDefaultInstance()))
              .setSchemaDescriptor(new LogicServer2PayServerServiceMethodDescriptorSupplier("reqCreateOrder"))
              .build();
        }
      }
    }
    return getReqCreateOrderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify,
      com.google.protobuf.Empty> getReqPayVerifyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqPayVerify",
      requestType = com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify,
      com.google.protobuf.Empty> getReqPayVerifyMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify, com.google.protobuf.Empty> getReqPayVerifyMethod;
    if ((getReqPayVerifyMethod = LogicServer2PayServerServiceGrpc.getReqPayVerifyMethod) == null) {
      synchronized (LogicServer2PayServerServiceGrpc.class) {
        if ((getReqPayVerifyMethod = LogicServer2PayServerServiceGrpc.getReqPayVerifyMethod) == null) {
          LogicServer2PayServerServiceGrpc.getReqPayVerifyMethod = getReqPayVerifyMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqPayVerify"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new LogicServer2PayServerServiceMethodDescriptorSupplier("reqPayVerify"))
              .build();
        }
      }
    }
    return getReqPayVerifyMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogicServer2PayServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2PayServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2PayServerServiceStub>() {
        @java.lang.Override
        public LogicServer2PayServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2PayServerServiceStub(channel, callOptions);
        }
      };
    return LogicServer2PayServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogicServer2PayServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2PayServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2PayServerServiceBlockingStub>() {
        @java.lang.Override
        public LogicServer2PayServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2PayServerServiceBlockingStub(channel, callOptions);
        }
      };
    return LogicServer2PayServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogicServer2PayServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2PayServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2PayServerServiceFutureStub>() {
        @java.lang.Override
        public LogicServer2PayServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2PayServerServiceFutureStub(channel, callOptions);
        }
      };
    return LogicServer2PayServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 逻辑服向充值服rpc service
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 请求充值服创建一个充值订单
     * </pre>
     */
    default void reqCreateOrder(com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqCreateOrderMethod(), responseObserver);
    }

    /**
     * <pre>
     * 请求充值服充值订单验证
     * </pre>
     */
    default void reqPayVerify(com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqPayVerifyMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LogicServer2PayServerService.
   * <pre>
   * 逻辑服向充值服rpc service
   * </pre>
   */
  public static abstract class LogicServer2PayServerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LogicServer2PayServerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LogicServer2PayServerService.
   * <pre>
   * 逻辑服向充值服rpc service
   * </pre>
   */
  public static final class LogicServer2PayServerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LogicServer2PayServerServiceStub> {
    private LogicServer2PayServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2PayServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2PayServerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 请求充值服创建一个充值订单
     * </pre>
     */
    public void reqCreateOrder(com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqCreateOrderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 请求充值服充值订单验证
     * </pre>
     */
    public void reqPayVerify(com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqPayVerifyMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LogicServer2PayServerService.
   * <pre>
   * 逻辑服向充值服rpc service
   * </pre>
   */
  public static final class LogicServer2PayServerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LogicServer2PayServerServiceBlockingStub> {
    private LogicServer2PayServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2PayServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2PayServerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 请求充值服创建一个充值订单
     * </pre>
     */
    public com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder reqCreateOrder(com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqCreateOrderMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 请求充值服充值订单验证
     * </pre>
     */
    public com.google.protobuf.Empty reqPayVerify(com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqPayVerifyMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LogicServer2PayServerService.
   * <pre>
   * 逻辑服向充值服rpc service
   * </pre>
   */
  public static final class LogicServer2PayServerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LogicServer2PayServerServiceFutureStub> {
    private LogicServer2PayServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2PayServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2PayServerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 请求充值服创建一个充值订单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder> reqCreateOrder(
        com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqCreateOrderMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 请求充值服充值订单验证
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> reqPayVerify(
        com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqPayVerifyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_CREATE_ORDER = 0;
  private static final int METHODID_REQ_PAY_VERIFY = 1;

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
        case METHODID_REQ_CREATE_ORDER:
          serviceImpl.reqCreateOrder((com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder) request,
              (io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder>) responseObserver);
          break;
        case METHODID_REQ_PAY_VERIFY:
          serviceImpl.reqPayVerify((com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
          getReqCreateOrderMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.ReqPublicPayServerCreateOrder,
              com.game.grpc.proto.GrpcProto.ResPublicPayServerCreateOrder>(
                service, METHODID_REQ_CREATE_ORDER)))
        .addMethod(
          getReqPayVerifyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.ReqPublicPayServerVerify,
              com.google.protobuf.Empty>(
                service, METHODID_REQ_PAY_VERIFY)))
        .build();
  }

  private static abstract class LogicServer2PayServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogicServer2PayServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogicServer2PayServerService");
    }
  }

  private static final class LogicServer2PayServerServiceFileDescriptorSupplier
      extends LogicServer2PayServerServiceBaseDescriptorSupplier {
    LogicServer2PayServerServiceFileDescriptorSupplier() {}
  }

  private static final class LogicServer2PayServerServiceMethodDescriptorSupplier
      extends LogicServer2PayServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LogicServer2PayServerServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (LogicServer2PayServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogicServer2PayServerServiceFileDescriptorSupplier())
              .addMethod(getReqCreateOrderMethod())
              .addMethod(getReqPayVerifyMethod())
              .build();
        }
      }
    }
    return result;
  }
}
