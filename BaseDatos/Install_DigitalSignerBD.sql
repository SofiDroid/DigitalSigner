USE [DigitalSigner]
GO
/****** Object:  Table [dbo].[BD_A_AUTUSU]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_AUTUSU](
	[ID_AUTORIDAD] [numeric](8, 0) NOT NULL,
	[ID_USUARIO] [numeric](8, 0) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_AUTUSU] PRIMARY KEY CLUSTERED 
(
	[ID_AUTORIDAD] ASC,
	[ID_USUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_CONFTIPODOC]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_CONFTIPODOC](
	[ID_CONFTIPODOC] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_TIPODOCUMENTO] [numeric](8, 0) NOT NULL,
	[ID_AUTORIDAD] [numeric](8, 0) NOT NULL,
	[EN_ORDEN] [numeric](8, 0) NOT NULL,
	[DS_FIRMAPOSX] [varchar](50) NOT NULL,
	[DS_FIRMAPOSY] [varchar](50) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_CONFTIPODOC] PRIMARY KEY CLUSTERED 
(
	[ID_CONFTIPODOC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_CONFVALUNI]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_CONFVALUNI](
	[ID_CONFVALUNI] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_UNIDAD] [numeric](8, 0) NOT NULL,
	[ID_CONFIGURACION] [numeric](8, 0) NOT NULL,
	[ID_CONFVALOR] [numeric](8, 0) NULL,
	[DS_VALORLIBRE] [varchar](255) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_CONFVALUNI] PRIMARY KEY CLUSTERED 
(
	[ID_CONFVALUNI] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_DOCEXTRA]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_DOCEXTRA](
	[ID_DOCEXTRA] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_DOCUMENTO] [numeric](8, 0) NOT NULL,
	[CO_FICHERO] [varchar](50) NOT NULL,
	[CO_EXTENSION] [varchar](50) NOT NULL,
	[BL_FICHERO] [image] NULL,
	[DS_RUTA] [varchar](255) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_DOCEXTRA] PRIMARY KEY CLUSTERED 
(
	[ID_DOCEXTRA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_DOCFIRMA]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_DOCFIRMA](
	[ID_DOCFIRMA] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_DOCUMENTO] [numeric](8, 0) NOT NULL,
	[ID_AUTORIDAD] [numeric](8, 0) NOT NULL,
	[EN_ORDEN] [numeric](8, 0) NOT NULL,
	[DS_FIRMAPOSX] [varchar](50) NOT NULL,
	[DS_FIRMAPOSY] [varchar](50) NOT NULL,
	[FE_FIRMA] [datetime] NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_DOCFIRMA] PRIMARY KEY CLUSTERED 
(
	[ID_DOCFIRMA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_DOCOBS]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_DOCOBS](
	[ID_DOCOBS] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[DS_OBSERVACIONES] [varchar](255) NULL,
	[ID_DOCUMENTO] [numeric](8, 0) NOT NULL,
	[ID_USUARIO] [numeric](8, 0) NULL,
	[ID_AUTORIDAD] [numeric](8, 0) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_DOCOBS] PRIMARY KEY CLUSTERED 
(
	[ID_DOCOBS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_DOCRECHAZO]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_DOCRECHAZO](
	[ID_DOCRECHAZO] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_DOCUMENTO] [numeric](8, 0) NOT NULL,
	[DS_OBSERVACIONES] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_DOCRECHAZO] PRIMARY KEY CLUSTERED 
(
	[ID_DOCRECHAZO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_HISTDOC]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_HISTDOC](
	[ID_HISTDOC] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_DOCUMENTO] [numeric](8, 0) NOT NULL,
	[ID_SITUACIONDOC] [numeric](8, 0) NOT NULL,
	[BL_DOCUMENTO] [image] NULL,
	[DS_RUTA] [varchar](255) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_HISTDOC] PRIMARY KEY CLUSTERED 
(
	[ID_HISTDOC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_HISTENTXML]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_HISTENTXML](
	[ID_HISTENTXML] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_ENTRADAXML] [numeric](8, 0) NOT NULL,
	[ID_SITUACIONXML] [numeric](8, 0) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_HISTENTXML] PRIMARY KEY CLUSTERED 
(
	[ID_HISTENTXML] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_HISTSALXML]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_HISTSALXML](
	[ID_HISTSALXML] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_SALIDAXML] [numeric](8, 0) NOT NULL,
	[ID_SITUACIONXML] [numeric](8, 0) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_HISTSALXML] PRIMARY KEY CLUSTERED 
(
	[ID_HISTSALXML] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_TIPOUSUOPCPER]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_TIPOUSUOPCPER](
	[ID_TIPOUSUARIO] [numeric](8, 0) NOT NULL,
	[ID_OPCIONMENU] [numeric](8, 0) NOT NULL,
	[ID_PERMISO] [numeric](8, 0) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_TIPOUSUOPCPER_1] PRIMARY KEY CLUSTERED 
(
	[ID_TIPOUSUARIO] ASC,
	[ID_OPCIONMENU] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_TOKENUSUARIO]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_TOKENUSUARIO](
	[ID_TOKENUSUARIO] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_USUARIO] [numeric](8, 0) NOT NULL,
	[DS_TOKEN] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_TOKENUSUARIO] PRIMARY KEY CLUSTERED 
(
	[ID_TOKENUSUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_UNIUSU]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_UNIUSU](
	[ID_UNIDAD] [numeric](8, 0) NOT NULL,
	[ID_USUARIO] [numeric](8, 0) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_UNIUSU] PRIMARY KEY CLUSTERED 
(
	[ID_UNIDAD] ASC,
	[ID_USUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_A_USUTIPOUSU]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_A_USUTIPOUSU](
	[ID_USUARIO] [numeric](8, 0) NOT NULL,
	[ID_TIPOUSUARIO] [numeric](8, 0) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_A_USUTIPOUSU] PRIMARY KEY CLUSTERED 
(
	[ID_USUARIO] ASC,
	[ID_TIPOUSUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_D_DOCUMENTO]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_D_DOCUMENTO](
	[ID_DOCUMENTO] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_DOCUMENTO] [varchar](50) NOT NULL,
	[DS_DOCUMENTO] [varchar](255) NOT NULL,
	[ID_TIPODOCUMENTO] [numeric](8, 0) NOT NULL,
	[BL_DOCUMENTO] [image] NULL,
	[CO_FICHERO] [varchar](50) NOT NULL,
	[CO_EXTENSION] [varchar](50) NOT NULL,
	[ID_SITUACIONDOC] [numeric](8, 0) NOT NULL,
	[DS_RUTA] [varchar](255) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_D_DOCUMENTO] PRIMARY KEY CLUSTERED 
(
	[ID_DOCUMENTO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_D_DOCUMENTO] UNIQUE NONCLUSTERED 
(
	[CO_DOCUMENTO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_D_ENTRADAXML]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_D_ENTRADAXML](
	[ID_ENTRADAXML] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[BL_XML] [image] NOT NULL,
	[ID_DOCUMENTO] [numeric](8, 0) NOT NULL,
	[ID_SITUACIONXML] [numeric](8, 0) NOT NULL,
	[DS_RUTA] [varchar](255) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_D_ENTRADAXML] PRIMARY KEY CLUSTERED 
(
	[ID_ENTRADAXML] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_D_LOGON]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_D_LOGON](
	[ID_LOGON] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_USUARIO] [numeric](8, 0) NULL,
	[DS_IP] [varchar](50) NULL,
	[DS_TOKEN] [varchar](255) NULL,
	[BO_ERROR] [bit] NOT NULL,
	[DS_LLAMADA] [varchar](255) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_D_LOGON] PRIMARY KEY CLUSTERED 
(
	[ID_LOGON] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_D_SALIDAXML]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_D_SALIDAXML](
	[ID_SALIDAXML] [numeric](8, 0) NOT NULL,
	[BL_SALIDAXML] [image] NOT NULL,
	[ID_DOCUMENTO] [numeric](8, 0) NULL,
	[ID_SITUACIONXML] [numeric](8, 0) NOT NULL,
	[DS_RUTA] [varchar](255) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_D_SALIDAXML] PRIMARY KEY CLUSTERED 
(
	[ID_SALIDAXML] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_AUTORIDAD]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_AUTORIDAD](
	[ID_AUTORIDAD] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_AUTORIDAD] [varchar](50) NOT NULL,
	[DS_AUTORIDAD] [varchar](255) NOT NULL,
	[ID_UNIDAD] [numeric](8, 0) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_AUTORIDAD] PRIMARY KEY CLUSTERED 
(
	[ID_AUTORIDAD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_AUTORIDAD] UNIQUE NONCLUSTERED 
(
	[CO_AUTORIDAD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_CONFIGURACION]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_CONFIGURACION](
	[ID_CONFIGURACION] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_CONFIGURACION] [varchar](50) NOT NULL,
	[DS_CONFIGURACION] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_CONFIGURACION] PRIMARY KEY CLUSTERED 
(
	[ID_CONFIGURACION] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_CONFIGURACION] UNIQUE NONCLUSTERED 
(
	[CO_CONFIGURACION] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_CONFVALOR]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_CONFVALOR](
	[ID_CONFVALOR] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[ID_CONFIGURACION] [numeric](8, 0) NOT NULL,
	[CO_CONFVALOR] [varchar](50) NOT NULL,
	[DS_CONFVALOR] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_CONFVALOR] PRIMARY KEY CLUSTERED 
(
	[ID_CONFVALOR] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_CONFVALOR] UNIQUE NONCLUSTERED 
(
	[CO_CONFVALOR] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_OPCIONMENU]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_OPCIONMENU](
	[ID_OPCIONMENU] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_OPCIONMENU] [varchar](50) NOT NULL,
	[DS_OPCIONMENU] [varchar](255) NOT NULL,
	[DS_TITULO] [varchar](50) NOT NULL,
	[DS_TOOLTIP] [varchar](50) NULL,
	[DS_RUTA] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_OPCIONMENU] PRIMARY KEY CLUSTERED 
(
	[ID_OPCIONMENU] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_OPCIONMENU] UNIQUE NONCLUSTERED 
(
	[CO_OPCIONMENU] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_PERMISO]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_PERMISO](
	[ID_PERMISO] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_PERMISO] [varchar](50) NOT NULL,
	[DS_PERMISO] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_PERMISO] PRIMARY KEY CLUSTERED 
(
	[ID_PERMISO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_PERMISO] UNIQUE NONCLUSTERED 
(
	[CO_PERMISO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_SITUACIONDOC]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_SITUACIONDOC](
	[ID_SITUACIONDOC] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_SITUACIONDOC] [varchar](50) NOT NULL,
	[DS_SITUACIONDOC] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_SITUACIONDOC] PRIMARY KEY CLUSTERED 
(
	[ID_SITUACIONDOC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_SITUACIONDOC] UNIQUE NONCLUSTERED 
(
	[CO_SITUACIONDOC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_SITUACIONXML]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_SITUACIONXML](
	[ID_SITUACIONXML] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_SITUACIONXML] [varchar](50) NOT NULL,
	[DS_SITUACIONXML] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_SITUACIONXML] PRIMARY KEY CLUSTERED 
(
	[ID_SITUACIONXML] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_SITUACIONXML] UNIQUE NONCLUSTERED 
(
	[CO_SITUACIONXML] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_TIPODOCUMENTO]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_TIPODOCUMENTO](
	[ID_TIPODOCUMENTO] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_TIPODOCUMENTO] [varchar](50) NOT NULL,
	[DS_TIPODOCUMENTO] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_TIPODOCUMENTO] PRIMARY KEY CLUSTERED 
(
	[ID_TIPODOCUMENTO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_TIPODOCUMENTO] UNIQUE NONCLUSTERED 
(
	[CO_TIPODOCUMENTO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_TIPOUSUARIO]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_TIPOUSUARIO](
	[ID_TIPOUSUARIO] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_TIPOUSUARIO] [varchar](50) NOT NULL,
	[DS_TIPOUSUARIO] [varchar](255) NOT NULL,
	[ID_UNIDAD] [numeric](8, 0) NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_TIPOUSUARIO] PRIMARY KEY CLUSTERED 
(
	[ID_TIPOUSUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_TIPOUSUARIO] UNIQUE NONCLUSTERED 
(
	[CO_TIPOUSUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_UNIDAD]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_UNIDAD](
	[ID_UNIDAD] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_UNIDAD] [varchar](50) NOT NULL,
	[DS_UNIDAD] [varchar](255) NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_UNIDAD] PRIMARY KEY CLUSTERED 
(
	[ID_UNIDAD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_UNIDAD] UNIQUE NONCLUSTERED 
(
	[CO_UNIDAD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BD_T_USUARIO]    Script Date: 13/09/2022 14:04:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BD_T_USUARIO](
	[ID_USUARIO] [numeric](8, 0) IDENTITY(1,1) NOT NULL,
	[CO_NIF] [varchar](50) NOT NULL,
	[CO_USUARIO] [varchar](50) NOT NULL,
	[CO_PASSWORD] [varchar](50) NOT NULL,
	[DS_NOMBRE] [varchar](50) NOT NULL,
	[DS_APELLIDO1] [varchar](50) NOT NULL,
	[DS_APELLIDO2] [varchar](50) NULL,
	[EN_INTENTOS] [numeric](1, 0) NOT NULL,
	[EN_INTENTOSMAX] [numeric](1, 0) NOT NULL,
	[BO_ADMIN] [bit] NOT NULL,
	[FE_ALTA] [datetime] NOT NULL,
	[FE_DESACTIVO] [datetime] NULL,
	[USUARIOBD] [varchar](50) NOT NULL,
	[TSTBD] [datetime] NOT NULL,
 CONSTRAINT [PK_BD_T_USUARIO] PRIMARY KEY CLUSTERED 
(
	[ID_USUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_BD_T_USUARIO] UNIQUE NONCLUSTERED 
(
	[CO_USUARIO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BD_D_LOGON] ADD  CONSTRAINT [DF_BD_D_LOGON_BO_ERROR]  DEFAULT ((0)) FOR [BO_ERROR]
GO
ALTER TABLE [dbo].[BD_T_USUARIO] ADD  CONSTRAINT [DF_BD_T_USUARIO_BO_ADMIN]  DEFAULT ((0)) FOR [BO_ADMIN]
GO
ALTER TABLE [dbo].[BD_A_AUTUSU]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_AUTUSU_BD_T_AUTORIDAD] FOREIGN KEY([ID_AUTORIDAD])
REFERENCES [dbo].[BD_T_AUTORIDAD] ([ID_AUTORIDAD])
GO
ALTER TABLE [dbo].[BD_A_AUTUSU] CHECK CONSTRAINT [FK_BD_A_AUTUSU_BD_T_AUTORIDAD]
GO
ALTER TABLE [dbo].[BD_A_AUTUSU]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_AUTUSU_BD_T_USUARIO] FOREIGN KEY([ID_USUARIO])
REFERENCES [dbo].[BD_T_USUARIO] ([ID_USUARIO])
GO
ALTER TABLE [dbo].[BD_A_AUTUSU] CHECK CONSTRAINT [FK_BD_A_AUTUSU_BD_T_USUARIO]
GO
ALTER TABLE [dbo].[BD_A_CONFTIPODOC]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_CONFTIPODOC_BD_T_AUTORIDAD] FOREIGN KEY([ID_AUTORIDAD])
REFERENCES [dbo].[BD_T_AUTORIDAD] ([ID_AUTORIDAD])
GO
ALTER TABLE [dbo].[BD_A_CONFTIPODOC] CHECK CONSTRAINT [FK_BD_A_CONFTIPODOC_BD_T_AUTORIDAD]
GO
ALTER TABLE [dbo].[BD_A_CONFTIPODOC]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_CONFTIPODOC_BD_T_TIPODOCUMENTO] FOREIGN KEY([ID_TIPODOCUMENTO])
REFERENCES [dbo].[BD_T_TIPODOCUMENTO] ([ID_TIPODOCUMENTO])
GO
ALTER TABLE [dbo].[BD_A_CONFTIPODOC] CHECK CONSTRAINT [FK_BD_A_CONFTIPODOC_BD_T_TIPODOCUMENTO]
GO
ALTER TABLE [dbo].[BD_A_CONFVALUNI]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_CONFVALUNI_BD_T_CONFIGURACION] FOREIGN KEY([ID_CONFIGURACION])
REFERENCES [dbo].[BD_T_CONFIGURACION] ([ID_CONFIGURACION])
GO
ALTER TABLE [dbo].[BD_A_CONFVALUNI] CHECK CONSTRAINT [FK_BD_A_CONFVALUNI_BD_T_CONFIGURACION]
GO
ALTER TABLE [dbo].[BD_A_CONFVALUNI]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_CONFVALUNI_BD_T_CONFVALOR] FOREIGN KEY([ID_CONFVALOR])
REFERENCES [dbo].[BD_T_CONFVALOR] ([ID_CONFVALOR])
GO
ALTER TABLE [dbo].[BD_A_CONFVALUNI] CHECK CONSTRAINT [FK_BD_A_CONFVALUNI_BD_T_CONFVALOR]
GO
ALTER TABLE [dbo].[BD_A_CONFVALUNI]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_CONFVALUNI_BD_T_UNIDAD] FOREIGN KEY([ID_UNIDAD])
REFERENCES [dbo].[BD_T_UNIDAD] ([ID_UNIDAD])
GO
ALTER TABLE [dbo].[BD_A_CONFVALUNI] CHECK CONSTRAINT [FK_BD_A_CONFVALUNI_BD_T_UNIDAD]
GO
ALTER TABLE [dbo].[BD_A_DOCEXTRA]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_DOCEXTRA_BD_D_DOCUMENTO] FOREIGN KEY([ID_DOCUMENTO])
REFERENCES [dbo].[BD_D_DOCUMENTO] ([ID_DOCUMENTO])
GO
ALTER TABLE [dbo].[BD_A_DOCEXTRA] CHECK CONSTRAINT [FK_BD_A_DOCEXTRA_BD_D_DOCUMENTO]
GO
ALTER TABLE [dbo].[BD_A_DOCFIRMA]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_DOCFIRMA_BD_D_DOCUMENTO] FOREIGN KEY([ID_DOCUMENTO])
REFERENCES [dbo].[BD_D_DOCUMENTO] ([ID_DOCUMENTO])
GO
ALTER TABLE [dbo].[BD_A_DOCFIRMA] CHECK CONSTRAINT [FK_BD_A_DOCFIRMA_BD_D_DOCUMENTO]
GO
ALTER TABLE [dbo].[BD_A_DOCFIRMA]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_DOCFIRMA_BD_T_AUTORIDAD] FOREIGN KEY([ID_AUTORIDAD])
REFERENCES [dbo].[BD_T_AUTORIDAD] ([ID_AUTORIDAD])
GO
ALTER TABLE [dbo].[BD_A_DOCFIRMA] CHECK CONSTRAINT [FK_BD_A_DOCFIRMA_BD_T_AUTORIDAD]
GO
ALTER TABLE [dbo].[BD_A_DOCOBS]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_DOCOBS_BD_D_DOCUMENTO] FOREIGN KEY([ID_DOCUMENTO])
REFERENCES [dbo].[BD_D_DOCUMENTO] ([ID_DOCUMENTO])
GO
ALTER TABLE [dbo].[BD_A_DOCOBS] CHECK CONSTRAINT [FK_BD_A_DOCOBS_BD_D_DOCUMENTO]
GO
ALTER TABLE [dbo].[BD_A_DOCOBS]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_DOCOBS_BD_T_AUTORIDAD] FOREIGN KEY([ID_AUTORIDAD])
REFERENCES [dbo].[BD_T_AUTORIDAD] ([ID_AUTORIDAD])
GO
ALTER TABLE [dbo].[BD_A_DOCOBS] CHECK CONSTRAINT [FK_BD_A_DOCOBS_BD_T_AUTORIDAD]
GO
ALTER TABLE [dbo].[BD_A_DOCOBS]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_DOCOBS_BD_T_USUARIO] FOREIGN KEY([ID_USUARIO])
REFERENCES [dbo].[BD_T_USUARIO] ([ID_USUARIO])
GO
ALTER TABLE [dbo].[BD_A_DOCOBS] CHECK CONSTRAINT [FK_BD_A_DOCOBS_BD_T_USUARIO]
GO
ALTER TABLE [dbo].[BD_A_DOCRECHAZO]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_DOCRECHAZO_BD_D_DOCUMENTO] FOREIGN KEY([ID_DOCUMENTO])
REFERENCES [dbo].[BD_D_DOCUMENTO] ([ID_DOCUMENTO])
GO
ALTER TABLE [dbo].[BD_A_DOCRECHAZO] CHECK CONSTRAINT [FK_BD_A_DOCRECHAZO_BD_D_DOCUMENTO]
GO
ALTER TABLE [dbo].[BD_A_HISTDOC]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_HISTDOC_BD_D_DOCUMENTO] FOREIGN KEY([ID_DOCUMENTO])
REFERENCES [dbo].[BD_D_DOCUMENTO] ([ID_DOCUMENTO])
GO
ALTER TABLE [dbo].[BD_A_HISTDOC] CHECK CONSTRAINT [FK_BD_A_HISTDOC_BD_D_DOCUMENTO]
GO
ALTER TABLE [dbo].[BD_A_HISTDOC]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_HISTDOC_BD_T_SITUACIONDOC] FOREIGN KEY([ID_SITUACIONDOC])
REFERENCES [dbo].[BD_T_SITUACIONDOC] ([ID_SITUACIONDOC])
GO
ALTER TABLE [dbo].[BD_A_HISTDOC] CHECK CONSTRAINT [FK_BD_A_HISTDOC_BD_T_SITUACIONDOC]
GO
ALTER TABLE [dbo].[BD_A_HISTENTXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_HISTENTXML_BD_D_ENTRADAXML] FOREIGN KEY([ID_ENTRADAXML])
REFERENCES [dbo].[BD_D_ENTRADAXML] ([ID_ENTRADAXML])
GO
ALTER TABLE [dbo].[BD_A_HISTENTXML] CHECK CONSTRAINT [FK_BD_A_HISTENTXML_BD_D_ENTRADAXML]
GO
ALTER TABLE [dbo].[BD_A_HISTENTXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_HISTENTXML_BD_T_SITUACIONXML] FOREIGN KEY([ID_SITUACIONXML])
REFERENCES [dbo].[BD_T_SITUACIONXML] ([ID_SITUACIONXML])
GO
ALTER TABLE [dbo].[BD_A_HISTENTXML] CHECK CONSTRAINT [FK_BD_A_HISTENTXML_BD_T_SITUACIONXML]
GO
ALTER TABLE [dbo].[BD_A_HISTSALXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_HISTSALXML_BD_D_SALIDAXML] FOREIGN KEY([ID_SALIDAXML])
REFERENCES [dbo].[BD_D_SALIDAXML] ([ID_SALIDAXML])
GO
ALTER TABLE [dbo].[BD_A_HISTSALXML] CHECK CONSTRAINT [FK_BD_A_HISTSALXML_BD_D_SALIDAXML]
GO
ALTER TABLE [dbo].[BD_A_HISTSALXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_HISTSALXML_BD_T_SITUACIONXML] FOREIGN KEY([ID_SITUACIONXML])
REFERENCES [dbo].[BD_T_SITUACIONXML] ([ID_SITUACIONXML])
GO
ALTER TABLE [dbo].[BD_A_HISTSALXML] CHECK CONSTRAINT [FK_BD_A_HISTSALXML_BD_T_SITUACIONXML]
GO
ALTER TABLE [dbo].[BD_A_TIPOUSUOPCPER]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_TIPOUSUOPCPER_BD_T_OPCIONMENU] FOREIGN KEY([ID_OPCIONMENU])
REFERENCES [dbo].[BD_T_OPCIONMENU] ([ID_OPCIONMENU])
GO
ALTER TABLE [dbo].[BD_A_TIPOUSUOPCPER] CHECK CONSTRAINT [FK_BD_A_TIPOUSUOPCPER_BD_T_OPCIONMENU]
GO
ALTER TABLE [dbo].[BD_A_TIPOUSUOPCPER]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_TIPOUSUOPCPER_BD_T_PERMISO] FOREIGN KEY([ID_PERMISO])
REFERENCES [dbo].[BD_T_PERMISO] ([ID_PERMISO])
GO
ALTER TABLE [dbo].[BD_A_TIPOUSUOPCPER] CHECK CONSTRAINT [FK_BD_A_TIPOUSUOPCPER_BD_T_PERMISO]
GO
ALTER TABLE [dbo].[BD_A_TIPOUSUOPCPER]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_TIPOUSUOPCPER_BD_T_TIPOUSUARIO] FOREIGN KEY([ID_TIPOUSUARIO])
REFERENCES [dbo].[BD_T_TIPOUSUARIO] ([ID_TIPOUSUARIO])
GO
ALTER TABLE [dbo].[BD_A_TIPOUSUOPCPER] CHECK CONSTRAINT [FK_BD_A_TIPOUSUOPCPER_BD_T_TIPOUSUARIO]
GO
ALTER TABLE [dbo].[BD_A_TOKENUSUARIO]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_TOKENUSUARIO_BD_T_USUARIO] FOREIGN KEY([ID_USUARIO])
REFERENCES [dbo].[BD_T_USUARIO] ([ID_USUARIO])
GO
ALTER TABLE [dbo].[BD_A_TOKENUSUARIO] CHECK CONSTRAINT [FK_BD_A_TOKENUSUARIO_BD_T_USUARIO]
GO
ALTER TABLE [dbo].[BD_A_UNIUSU]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_UNIUSU_BD_T_UNIDAD] FOREIGN KEY([ID_UNIDAD])
REFERENCES [dbo].[BD_T_UNIDAD] ([ID_UNIDAD])
GO
ALTER TABLE [dbo].[BD_A_UNIUSU] CHECK CONSTRAINT [FK_BD_A_UNIUSU_BD_T_UNIDAD]
GO
ALTER TABLE [dbo].[BD_A_UNIUSU]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_UNIUSU_BD_T_USUARIO] FOREIGN KEY([ID_USUARIO])
REFERENCES [dbo].[BD_T_USUARIO] ([ID_USUARIO])
GO
ALTER TABLE [dbo].[BD_A_UNIUSU] CHECK CONSTRAINT [FK_BD_A_UNIUSU_BD_T_USUARIO]
GO
ALTER TABLE [dbo].[BD_A_USUTIPOUSU]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_USUTIPOUSU_BD_T_TIPOUSUARIO] FOREIGN KEY([ID_TIPOUSUARIO])
REFERENCES [dbo].[BD_T_TIPOUSUARIO] ([ID_TIPOUSUARIO])
GO
ALTER TABLE [dbo].[BD_A_USUTIPOUSU] CHECK CONSTRAINT [FK_BD_A_USUTIPOUSU_BD_T_TIPOUSUARIO]
GO
ALTER TABLE [dbo].[BD_A_USUTIPOUSU]  WITH CHECK ADD  CONSTRAINT [FK_BD_A_USUTIPOUSU_BD_T_USUARIO] FOREIGN KEY([ID_USUARIO])
REFERENCES [dbo].[BD_T_USUARIO] ([ID_USUARIO])
GO
ALTER TABLE [dbo].[BD_A_USUTIPOUSU] CHECK CONSTRAINT [FK_BD_A_USUTIPOUSU_BD_T_USUARIO]
GO
ALTER TABLE [dbo].[BD_D_DOCUMENTO]  WITH CHECK ADD  CONSTRAINT [FK_BD_D_DOCUMENTO_BD_T_SITUACIONDOC] FOREIGN KEY([ID_SITUACIONDOC])
REFERENCES [dbo].[BD_T_SITUACIONDOC] ([ID_SITUACIONDOC])
GO
ALTER TABLE [dbo].[BD_D_DOCUMENTO] CHECK CONSTRAINT [FK_BD_D_DOCUMENTO_BD_T_SITUACIONDOC]
GO
ALTER TABLE [dbo].[BD_D_DOCUMENTO]  WITH CHECK ADD  CONSTRAINT [FK_BD_D_DOCUMENTO_BD_T_TIPODOCUMENTO] FOREIGN KEY([ID_TIPODOCUMENTO])
REFERENCES [dbo].[BD_T_TIPODOCUMENTO] ([ID_TIPODOCUMENTO])
GO
ALTER TABLE [dbo].[BD_D_DOCUMENTO] CHECK CONSTRAINT [FK_BD_D_DOCUMENTO_BD_T_TIPODOCUMENTO]
GO
ALTER TABLE [dbo].[BD_D_ENTRADAXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_D_ENTRADAXML_BD_D_DOCUMENTO] FOREIGN KEY([ID_DOCUMENTO])
REFERENCES [dbo].[BD_D_DOCUMENTO] ([ID_DOCUMENTO])
GO
ALTER TABLE [dbo].[BD_D_ENTRADAXML] CHECK CONSTRAINT [FK_BD_D_ENTRADAXML_BD_D_DOCUMENTO]
GO
ALTER TABLE [dbo].[BD_D_ENTRADAXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_D_ENTRADAXML_BD_T_SITUACIONXML] FOREIGN KEY([ID_SITUACIONXML])
REFERENCES [dbo].[BD_T_SITUACIONXML] ([ID_SITUACIONXML])
GO
ALTER TABLE [dbo].[BD_D_ENTRADAXML] CHECK CONSTRAINT [FK_BD_D_ENTRADAXML_BD_T_SITUACIONXML]
GO
ALTER TABLE [dbo].[BD_D_LOGON]  WITH CHECK ADD  CONSTRAINT [FK_BD_D_LOGON_BD_T_USUARIO] FOREIGN KEY([ID_USUARIO])
REFERENCES [dbo].[BD_T_USUARIO] ([ID_USUARIO])
GO
ALTER TABLE [dbo].[BD_D_LOGON] CHECK CONSTRAINT [FK_BD_D_LOGON_BD_T_USUARIO]
GO
ALTER TABLE [dbo].[BD_D_SALIDAXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_D_SALIDAXML_BD_D_DOCUMENTO] FOREIGN KEY([ID_DOCUMENTO])
REFERENCES [dbo].[BD_D_DOCUMENTO] ([ID_DOCUMENTO])
GO
ALTER TABLE [dbo].[BD_D_SALIDAXML] CHECK CONSTRAINT [FK_BD_D_SALIDAXML_BD_D_DOCUMENTO]
GO
ALTER TABLE [dbo].[BD_D_SALIDAXML]  WITH CHECK ADD  CONSTRAINT [FK_BD_D_SALIDAXML_BD_T_SITUACIONXML] FOREIGN KEY([ID_SITUACIONXML])
REFERENCES [dbo].[BD_T_SITUACIONXML] ([ID_SITUACIONXML])
GO
ALTER TABLE [dbo].[BD_D_SALIDAXML] CHECK CONSTRAINT [FK_BD_D_SALIDAXML_BD_T_SITUACIONXML]
GO
ALTER TABLE [dbo].[BD_T_AUTORIDAD]  WITH CHECK ADD  CONSTRAINT [FK_BD_T_AUTORIDAD_BD_T_UNIDAD] FOREIGN KEY([ID_UNIDAD])
REFERENCES [dbo].[BD_T_UNIDAD] ([ID_UNIDAD])
GO
ALTER TABLE [dbo].[BD_T_AUTORIDAD] CHECK CONSTRAINT [FK_BD_T_AUTORIDAD_BD_T_UNIDAD]
GO
ALTER TABLE [dbo].[BD_T_CONFVALOR]  WITH CHECK ADD  CONSTRAINT [FK_BD_T_CONFVALOR_BD_T_CONFIGURACION] FOREIGN KEY([ID_CONFIGURACION])
REFERENCES [dbo].[BD_T_CONFIGURACION] ([ID_CONFIGURACION])
GO
ALTER TABLE [dbo].[BD_T_CONFVALOR] CHECK CONSTRAINT [FK_BD_T_CONFVALOR_BD_T_CONFIGURACION]
GO
ALTER TABLE [dbo].[BD_T_TIPOUSUARIO]  WITH CHECK ADD  CONSTRAINT [FK_BD_T_TIPOUSUARIO_BD_T_UNIDAD] FOREIGN KEY([ID_UNIDAD])
REFERENCES [dbo].[BD_T_UNIDAD] ([ID_UNIDAD])
GO
ALTER TABLE [dbo].[BD_T_TIPOUSUARIO] CHECK CONSTRAINT [FK_BD_T_TIPOUSUARIO_BD_T_UNIDAD]
GO
